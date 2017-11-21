package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.tokaigroup.reception.common.MailTemplate;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKanriDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKLifeDao;
import jp.co.tokaigroup.reception.dao.RcpTMailRirekiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuKLife;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.RcpTMailRireki;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.mail.VelocityEmail;
import jp.co.tokaigroup.si.fw.mail.VelocityWrapper;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB013DisclosureMailSendModel;
import jp.co.tokaigroup.tlcss_b2b.dto.TB013DisclosureMailSendDto;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TORES公開メール送信画面サービス実装クラス。
 *
 * @author k003856
 * @version 5.0 2015/09/08
 * @version 5.1 2016/07/14 H.Hirai 複数請求先対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB013DisclosureMailSendServiceImpl extends TLCSSB2BBaseService
		implements TB013DisclosureMailSendService {

	// メールテンプレート項目　キー
	/** メールテンプレート項目 キー 不動産・管理会社名 */
	private static final String MAIL_KEY_REAL_ESTATE_NAME = "realEstateName";
	/** メールテンプレート項目 キー 遷移先URL */
	private static final String MAIL_KEY_TRANSITION_URL = "transitionUrl";
	/** メールテンプレート項目 キー システム問い合わせ先電話番号 */
	private static final String MAIL_KEY_CONTACT_INFORMAITION = "contactInformation";
	/** メールテンプレート項目 キー 返信メールアドレス */
	private static final String MAIL_KEY_REPLY_MAIL_ADDRESS = "returnMailAddress";
	/** メールテンプレート項目 キー 送信元会社名 */
	private static final String MAIL_KEY_NOTICE_COMPAY_NAME = "noticeCompanyNm";

	/** メール情報作成区分 1：初期表示 */
	private static final String MAIL_INFO_KBN_INIT = "1";
	/** メール情報作成区分 2：メール送信 */
	private static final String MAIL_INFO_KBN_MAIL_SEND = "2";

	/** リセプション問い合わせテーブルDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション顧客契約ライフサポートマスタDAO */
	@Autowired
	private RcpMKokyakuKLifeDao kokyakuKLifeDao;

	/** リセプション顧客付随管理会社情報マスタDAO */
	@Autowired
	private RcpMKokyakuFKanriDao kokyakuFuzuiDao;

	/** 公開メール送信履歴マスタ-DAO */
	@Autowired
	private RcpTMailRirekiDao mailRirekiDao;

	/** 外部サイトシステム 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** リセプション会社マスタDAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model TORES公開メール送信画面モデル
	 * @return TORES公開メール送信画面モデル
	 */
	public TB013DisclosureMailSendModel getInitInfo(TB013DisclosureMailSendModel model) {

		// 問い合わせ情報取得
		RcpTToiawase toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// 問い合わせ情報がない場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setToiawase(toiawase);

		// 問い合わせ情報に紐付く顧客情報取得
		RcpMKokyaku kokyaku = kokyakuKihon.getKokyakuInfo(toiawase.getKokyakuId());
		model.setKokyaku(kokyaku);

		// 会社情報取得
		TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(toiawase.getCreKaishaId());
		if (kaisha == null) {
			// 会社情報がない場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}
		model.setKaishaId(kaisha.getKaishaId());

		
		if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawase.getIraishaFlg())) {
			// 依頼者フラグが「顧客基本情報と同じ」の場合、顧客契約ライフサポート情報を取得
			RcpMKokyakuKLife kokyakuKLife = kokyakuKLifeDao.selectByPrimaryKey(
					kokyaku.getKokyakuId(), new BigDecimal(1));
			model.setKokyakuKLife(kokyakuKLife);
		}

		// 送信先メールアドレス取得
		// 請求先顧客情報リスト取得
		List<RcpMKokyaku> tmpKokyakuList = kokyakuDao.selectSeikyusakiKokyakuList(kokyaku.getKokyakuId());
		List<RcpMKokyaku> seikyusakiKokyakuList = new ArrayList<RcpMKokyaku>();
		// 顧客区分「1：管理会社（オーナー）」以外のリスト情報を除外する
		for (RcpMKokyaku kokyakuInfo : tmpKokyakuList) {

			if (!kokyakuInfo.isKokyakuKbnFudosan()) {
				// 「1：管理会社（オーナー）」以外の場合、次レコードへ
				continue;
			}

			seikyusakiKokyakuList.add(kokyakuInfo);
		}

		if ((seikyusakiKokyakuList == null || seikyusakiKokyakuList.isEmpty())
				&& !kokyaku.isKokyakuKbnFudosan()) {
			// 請求先顧客が存在しないかつ、自分自身の顧客区分が不動産・管理会社でない場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0036", "請求先顧客", "（請求先顧客情報を確認して下さい）"));
		}

		if (!seikyusakiKokyakuList.isEmpty()) {
			// 請求先顧客が存在する場合、1件目の請求先顧客IDを設定
			model.setSeikyusakiKokyakuId(seikyusakiKokyakuList.get(0).getKokyakuId());
		} else {
			model.setSeikyusakiKokyakuId(kokyaku.getKokyakuId());
		}

		List<RcpMKokyakuFKanri> kokyakuFKanriList = new ArrayList<RcpMKokyakuFKanri>();
		if (!seikyusakiKokyakuList.isEmpty()) {
			// 請求先顧客情報リストが1件以上存在する場合

			for (RcpMKokyaku seikyusakiKokyaku : seikyusakiKokyakuList) {

				// 顧客付随管理会社情報取得
				RcpMKokyakuFKanri kokyakuFKanriInfo = kokyakuFuzuiDao
						.selectByPrimaryKey(seikyusakiKokyaku.getKokyakuId());

				if (kokyakuFKanriInfo != null
						&& (StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress1())
								|| StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress2())
								|| StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress3())
						)
				) {

					// 顧客付随管理会社情報が取得でき、
					// 対応報告メールアドレス１～３のいづれかがNULL以外の場合
					kokyakuFKanriList.add(kokyakuFKanriInfo);
				}
			}
		} else {
			// 請求先顧客情報リストが存在しない場合

			// 顧客付随管理会社情報取得
			RcpMKokyakuFKanri kokyakuFKanriInfo = kokyakuFuzuiDao
					.selectByPrimaryKey(kokyaku.getKokyakuId());

			if (kokyakuFKanriInfo != null
					&& (StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress1())
							|| StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress2())
							|| StringUtils.isNotBlank(kokyakuFKanriInfo.getTaioMailAddress3())
					)
			) {

				// 顧客付随管理会社情報が取得でき、
				// 対応報告メールアドレス１～３のいづれかがNULL以外の場合
				kokyakuFKanriList.add(kokyakuFKanriInfo);
			}
		}

		// 顧客付随管理会社情報マスタEntityが0件の場合、エラー
		if (kokyakuFKanriList.isEmpty()) {
			throw new ValidationException(new ValidationPack().addError("MSG0037",
					"送信先メールアドレス", "（顧客付随情報【対応報告通知情報】のメールアドレスを確認して下さい）"));
		}
		model.setKokyakuFKanriList(kokyakuFKanriList);

		// 件名
		model.setSubject(createSubject(model));

		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

		// 送信元メールアドレス
		model.setSenderMailAddress(userContext.getSystgemContstantAsString(
				RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ADDRESS));
		// BCCメールアドレス
		model.setBccMailAddress(userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_BCC_ADDRESS));

		// メール本文取得処理
		model = getMailInfo(model, MAIL_INFO_KBN_INIT);

		return model;
	}

	/**
	 * メール情報取得処理を行います。
	 *
	 * @param model TORES公開メール送信画面モデル
	 * @param mailInfoKbn メール情報作成区分
	 * @return TORES公開メール送信画面モデル
	 */
	private TB013DisclosureMailSendModel getMailInfo(TB013DisclosureMailSendModel model, String mailInfoKbn) {
		try {

			if (MAIL_INFO_KBN_INIT.equals(mailInfoKbn)) {
				// 初期表示処理の場合

				List<TB013DisclosureMailSendDto> disclosureMailSendList = new ArrayList<TB013DisclosureMailSendDto>();

				// 取得した顧客付随管理会社リスト分処理
				for (RcpMKokyakuFKanri kokyakuFKanri : model.getKokyakuFKanriList()) {

					// 請求先顧客情報取得
					RcpMKokyaku seikyusakiKokyaku = kokyakuDao.selectByPrimaryKey(kokyakuFKanri.getKokyakuId());

					// 会社情報取得
					TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(model.getKaishaId());
					if (kaisha == null) {
						// 会社情報が取得できなかった場合、エラー
						throw new ValidationException(new ValidationPack().addError("MSG0003"));
					}

					// 置換文字列Map生成
					VelocityWrapper wrapper = new VelocityWrapper(
							MailTemplate.TOIAWASE_PUBLISH_INFORMATION_MAIL_FILE_NAME);

					TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

					// 遷移先URLを生成
					String transitionUrl;
					if (model.isFromToiawaseEntry()) {
						// 問い合わせ登録画面からの遷移の場合、問い合わせ内容詳細へのリンクを作成
						transitionUrl = userContext.getSystgemContstantAsString(
								RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL)
										+ "externalLogin.action?actionURL=/inquiryDetailInit&toiawaseNo="
										+ model.getToiawaseNo() + "&dispKbn=2"; 
					} else {
						// 依頼登録画面からの遷移の場合、依頼内容詳細へのリンクを作成
						transitionUrl = userContext.getSystgemContstantAsString(
								RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL)
						+ "externalLogin.action?actionURL=/requestDetail&toiawaseNo="
						+ model.getToiawaseNo() + "&toiawaseRirekiNo="
						+ model.getToiawaseRirekiNo().toString() + "&dispKbn=3";
					} 

					// 置換文字列
					// 不動産・管理会社名
					wrapper.put(MAIL_KEY_REAL_ESTATE_NAME, seikyusakiKokyaku.getKanjiNm());

					// 遷移先URL
					wrapper.put(MAIL_KEY_TRANSITION_URL, transitionUrl);

					// 連絡先
					wrapper.put(MAIL_KEY_CONTACT_INFORMAITION, kaisha.getRenrakusaki());
					
					// 返信メールアドレス
					wrapper.put(MAIL_KEY_REPLY_MAIL_ADDRESS, StringUtils.defaultString(kaisha.getMailAddress()));
					
					// 送信元会社名
					wrapper.put(MAIL_KEY_NOTICE_COMPAY_NAME, kaisha.getTsuchiKaishaNm());

					String mailText = "";
					try {
						// テンプレート内を置換して、本文に設定
						mailText = wrapper.merge();
					} catch (ResourceNotFoundException e) {
						throw new ApplicationException(e);
					} catch (ParseErrorException e) {
						throw new ApplicationException(e);
					} catch (MethodInvocationException e) {
						throw new ApplicationException(e);
					} catch (IOException e) {
						throw new ApplicationException(e);
					}

					// 作成した情報をＤＴＯに設定
					TB013DisclosureMailSendDto disclosureMailSend = new TB013DisclosureMailSendDto();
					disclosureMailSend.setSeikyusakiKokyakuId(seikyusakiKokyaku.getKokyakuId());
					disclosureMailSend.setSeikyusakiKokyakuNm(seikyusakiKokyaku.getKokyakuIdWithKanjiNm());
					List<String> taioMailAddressList = new ArrayList<String>();
					if (StringUtils.isNotBlank(kokyakuFKanri.getTaioMailAddress1())) {
						taioMailAddressList.add(kokyakuFKanri.getTaioMailAddress1());
					}
					if (StringUtils.isNotBlank(kokyakuFKanri.getTaioMailAddress2())) {
						taioMailAddressList.add(kokyakuFKanri.getTaioMailAddress2());
					}
					if (StringUtils.isNotBlank(kokyakuFKanri.getTaioMailAddress3())) {
						taioMailAddressList.add(kokyakuFKanri.getTaioMailAddress3());
					}
					String taioMailAddress = StringUtils.join(taioMailAddressList, ",");
					disclosureMailSend.setTaioMailAddress(taioMailAddress);
					disclosureMailSend.setMailText(mailText);
					disclosureMailSendList.add(disclosureMailSend);
				}

				model.setDisclosureMailSendList(disclosureMailSendList);
				model.setDisclosureMailSendListSize(disclosureMailSendList.size());
			} else {
				// メール送信処理の場合

				// 請求先顧客情報取得
				RcpMKokyaku seikyusakiKokyaku = kokyakuDao.selectByPrimaryKey(model.getSeikyusakiKokyakuId());

				// 会社情報取得
				TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(model.getKaishaId());
				if (kaisha == null) {
					// 会社情報が取得できなかった場合、エラー
					throw new ValidationException(new ValidationPack().addError("MSG0003"));
				}

				// 置換文字列Map生成
				VelocityWrapper wrapper = new VelocityWrapper(
						MailTemplate.TOIAWASE_PUBLISH_INFORMATION_MAIL_FILE_NAME);

				TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

				// 遷移先URLを生成
				String transitionUrl;
				if (model.isFromToiawaseEntry()) {
					// 問い合わせ登録画面からの遷移の場合、問い合わせ内容詳細へのリンクを作成
					transitionUrl = userContext.getSystgemContstantAsString(
							RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL)
									+ "externalLogin.action?actionURL=/inquiryDetailInit&toiawaseNo="
									+ model.getToiawaseNo() + "&dispKbn=2"; 
				} else {
					// 依頼登録画面からの遷移の場合、依頼内容詳細へのリンクを作成
					transitionUrl = userContext.getSystgemContstantAsString(
							RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL)
					+ "externalLogin.action?actionURL=/requestDetail&toiawaseNo="
					+ model.getToiawaseNo() + "&toiawaseRirekiNo="
					+ model.getToiawaseRirekiNo().toString() + "&dispKbn=3";
				} 

				// 置換文字列
				// 不動産・管理会社名
				wrapper.put(MAIL_KEY_REAL_ESTATE_NAME, seikyusakiKokyaku.getKanjiNm());

				// 遷移先URL
				wrapper.put(MAIL_KEY_TRANSITION_URL, transitionUrl);

				// 連絡先
				wrapper.put(MAIL_KEY_CONTACT_INFORMAITION, kaisha.getRenrakusaki());
				
				// 返信メールアドレス
				wrapper.put(MAIL_KEY_REPLY_MAIL_ADDRESS, StringUtils.defaultString(kaisha.getMailAddress()));
				
				// 送信元会社名
				wrapper.put(MAIL_KEY_NOTICE_COMPAY_NAME, kaisha.getTsuchiKaishaNm());

				model.setWrapper(wrapper);
			}

		} catch (Exception e) {
			// その他例外
			throw new ApplicationException(e);
		}

		return model;
	}

	/**
	 * メール送信処理を行います。
	 *
	 * @param model TORES公開メール送信画面モデル
	 */
	@Transactional(value="txManager")
	public void executeSendMail(TB013DisclosureMailSendModel model) {
		// メール本文取得処理
		model = getMailInfo(model, MAIL_INFO_KBN_MAIL_SEND);

		// 外部サイトアクセスログの登録
		tbAccesslogDao.insert(TB013DisclosureMailSendModel.GAMEN_NM,
				TB013DisclosureMailSendModel.BUTTON_NM_MAIL_SEND, createKensakuJoken(model));

		try {
			// システムマスタから情報を取得

			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// 返信メールアドレス
			String returnMailAddress = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_RETURN_MAIL_ADDRESS);

			// 置換文字列Map生成
			VelocityWrapper wrapper = model.getWrapper();

			VelocityEmail email = new VelocityEmail();

			// 差出元アドレス
			email.setFrom(userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ADDRESS));
			// 認証
			email.setAuthenticator(new DefaultAuthenticator(
					userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ACCOUNT),
					userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_PASSWORD)));
			// 宛先
			for (String address : model.getTaioMailAddressList()) {
				email.addTo(address);
			}

			// 宛先BCC
			String bcc = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_BCC_ADDRESS);
			if (StringUtils.isNotBlank(bcc)) {
				email.addBcc(StringUtils.split(bcc, ","));
			}
			// 返信メールアドレス
			email.addReplyTo(returnMailAddress);
			// 件名
			email.setSubject(model.getSubject());
			// 置換文字列Mapの設定
			email.setVelocityWrapper(wrapper);

			// メール送信
			email.send();

			String rirekiNo;
			if (model.isFromToiawaseEntry()){
				rirekiNo = model.getToiawaseNo();
			} else{
				rirekiNo = model.getToiawaseNo() + "-"
					+ StringUtils.leftPad(model.getToiawaseRirekiNo().toString(), 3, "0");
			}

			// 公開メール送信履歴情報の登録
			mailRirekiDao.insertOrUpdate(createRirekiInfo(rirekiNo,model.getSubject()));

		} catch (EmailException e) {
			// メール送信に失敗した場合
			throw new ApplicationException(e);
		} catch (Exception e) {
			// その他例外
			throw new ApplicationException(e);
		}
	}

	/**
	 * 画面に表示する件名を生成します。
	 *
	 * @param model TORES公開メール送信画面モデル
	 * @return 画面に表示する件名
	 */
	private String createSubject(TB013DisclosureMailSendModel model) {
		StringBuilder subject = new StringBuilder("");

		if (StringUtils.isNotBlank(model.getSubject())) {
			// 既に件名が設定されている場合（初回表示でない）は、そのまま
			return model.getSubject();
		}

		RcpTToiawase toiawase = model.getToiawase();

		subject.append("【】（");
		if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawase.getIraishaFlg())) {
			// 依頼者フラグが「顧客基本情報と同じ」の場合
			subject.append(StringUtils.defaultString(model.getKokyaku().getJusho5()));
		} else {
			// 依頼者フラグが「異なる」の場合
			subject.append(model.getKokyaku().getKanjiNm());
		}
		subject.append("・");
		if (RcpTToiawase.IRAI_FLG_ONAJI.equals(toiawase.getIraishaFlg())) {
			// 依頼者フラグが「顧客基本情報と同じ」の場合
			if (model.getKokyakuKLife() != null) {
				subject.append(model.getKokyakuKLife().getRoomNo());
			}
		} else {
			// 依頼者フラグが「異なる」の場合
			subject.append(StringUtils.defaultString(toiawase.getIraishaRoomNo()));
		}

		subject.append("号室・");
		subject.append(model.getToiawaseNo());
		subject.append("）");

		return subject.toString();
	}
	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model TORES公開メール送信画面モデル
	 * @return アクセスログに登録する検索条件
	 */
	private String createKensakuJoken(TB013DisclosureMailSendModel model) {
		
		return "shoriKbn=" + model.getShoriKbn().toString() + ","
				+ "toiawaseNo=" + model.getToiawaseNo() + ","
				+ "toiawaseRirekiNo=" + String.valueOf(model.getToiawaseRirekiNo());
	}
	/**
	 * 公開メール送信履歴マスタ-登録を行うための公開メール送信履歴情報を生成します。
	 *
	 * @param rirekiNo 問合せ履歴Ｎｏ, subject メール件名
	 * @return 公開メール送信履歴
	 */
	private RcpTMailRireki createRirekiInfo(String rirekiNo,String subject) {
		RcpTMailRireki rireki = new RcpTMailRireki();

		rireki.setRirekiNo(rirekiNo);
		rireki.setSubject(subject);
		rireki.setCreId(getUserContext().getLoginId());
		rireki.setLastUpdId(getUserContext().getLoginId());

		return rireki;
	}
}
