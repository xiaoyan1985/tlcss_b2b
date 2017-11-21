package jp.co.tokaigroup.tlcss_b2b.master.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.MailTemplate;
import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.RcpMComCdExternalSiteDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuFKanriDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuKanrenDao;
import jp.co.tokaigroup.reception.dao.TbMGrpKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMKaishaDao;
import jp.co.tokaigroup.reception.dao.TbMRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.dto.RC02BKokyakuNodeDto;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMKokyakuFKanri;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbMGrpKokyaku;
import jp.co.tokaigroup.reception.entity.TbMKaisha;
import jp.co.tokaigroup.reception.entity.TbMRefKokyaku;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.mail.VelocityEmail;
import jp.co.tokaigroup.si.fw.mail.VelocityWrapper;
import jp.co.tokaigroup.si.fw.message.MessageBean;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.EncryptionUtil;
import jp.co.tokaigroup.si.fw.util.NullExclusionToStringBuilder;
import jp.co.tokaigroup.si.fw.util.PasswordGenerator;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.master.model.TB102UserMasterEntryModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザーマスタ登録サービス実装クラス。
 *
 * @author k002849
 * @version 1.0 2014/05/28
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB102UserMasterEntryServiceImpl extends TLCSSB2BBaseService
		implements TB102UserMasterEntryService {

	/** メール区分 1:ユーザー登録完了メール */
	private static final int MAIL_KBN_ENTRY = 1;
	/** メール区分 2:パスワード再発行メール */
	private static final int MAIL_KBN_PASSWORD_REISSUE = 2;

	// メールテンプレート項目　キー
	/** メールテンプレート項目 キー ユーザー名 */
	private static final String MAIL_KEY_USER_NAME = "userName";
	/** メールテンプレート項目 キー システム名 */
	private static final String MAIL_KEY_SYSTEM_NAME = "systemName";
	/** メールテンプレート項目 キー システムのURL */
	private static final String MAIL_KEY_SYSTEM_URL = "systemUrl";
	/** メールテンプレート項目 キー システム問い合わせ先電話番号 */
	private static final String MAIL_KEY_CONTACT_INFORMAITION = "contactInformation";
	/** メールテンプレート項目 キー 返信メールアドレス */
	private static final String MAIL_KEY_RETURN_MAIL_ADDRESS = "returnMailAddress";
	/** メールテンプレート項目 キー 発行した暫定パスワード */
	private static final String MAIL_KEY_TEMP_PASSWORD = "tempPassword";
	/** メールテンプレート項目 キー パスワード期限日 */
	private static final String MAIL_KEY_PASSWD_KIGEN_DT = "passwdKigenDt";
	/** メールテンプレート項目 キー 曜日 */
	private static final String MAIL_KEY_DAY_OF_WEEK = "dayOfWeek";
	/** メールテンプレート項目 キー サポートセンター受付時間 */
	private static final String MAIL_KEY_SUPPORT_RECEPTION_TIME = "supportReceptionTime";

	/** 外部サイトユーザーマスタDAO */
	@Autowired
	private TbMUserDao userDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** リセプション顧客付随管理会社情報マスタDAO */
	@Autowired
	private RcpMKokyakuFKanriDao kokyakuFKanriDao;

	/** リセプション業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;

	/** リセプション共通コードマスタDAO（外部サイト用） */
	@Autowired
	private RcpMComCdExternalSiteDao comCdDao;

	/** 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/** グループ顧客マスタDAO */
	@Autowired
	private TbMGrpKokyakuDao grpKokyakuDao;

	/** 顧客関連マスタDAO */
	@Autowired
	private RcpMKokyakuKanrenDao kokyakuKanrenDao;

	/** 参照顧客マスタDAO */
	@Autowired
	private TbMRefKokyakuDao refKokyakuDao;
	
	/** 会社マスタDAO */
	@Autowired
	private TbMKaishaDao kaishaDao;

	/**
	 * 初期表示処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @return ユーザーマスタ登録画面モデル
	 */
	public TB102UserMasterEntryModel getInitInfo(TB102UserMasterEntryModel model) {
		Map<String, List<RcpMComCd>> comKbnMap =
			comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		// 権限リストの取得
		List<RcpMComCd> roleList = comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_USER_AUTHORITY);

		model.setRoleList(roleList);

		return model;
	}

	/**
	 * ユーザー情報取得処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @return ユーザーマスタ登録画面モデル
	 */
	public TB102UserMasterEntryModel getUserInfo(TB102UserMasterEntryModel model) {
		// 初期表示処理呼び出し
		model = this.getInitInfo(model);

		// ユーザー情報の取得
		TbMUser user = userDao.selectByPrimaryKey(model.getSeqNo());
		if (user == null) {
			// ユーザー情報が存在しない場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		if (user.isInhouse()) {
			model.setTokaiKokyakuList(new ArrayList<RcpMKokyaku>());
			// 権限が「TOKAI管理者」または、「TOKAI一般」の場合
			List<TbMGrpKokyaku> grpKokyakuList = grpKokyakuDao.selectBy(user.getSeqNo(), null);
			for (TbMGrpKokyaku grpKokyaku : grpKokyakuList) {
				model.getTokaiKokyakuList().add(kokyakuDao.selectByPrimaryKey(grpKokyaku.getRefKokyakuId()));
			}
		} else if (user.isRealEstate()) {
			// 権限が「管理会社」の場合
			// 参照顧客ＩＤ欄の顧客名取得
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(user.getKokyakuId());
			if (kokyaku == null) {
				throw new ApplicationException("顧客マスタ情報が存在しません。");
			}
			user.setKokyakuNm(kokyaku.getKanjiNm());

			// 顧客階層DTOリスト設定
			model.setKokyakuNodeList(getRefKokyaku(user.getKokyakuId(), true));

			// 参照顧客情報取得
			List<TbMRefKokyaku> refKokuakuList = refKokyakuDao.selectBy(user.getKokyakuId(), null);
			List<MessageBean> duplicateContentList = new ArrayList<MessageBean>();

			// 参照顧客情報の件数分、繰り返す
			for (TbMRefKokyaku refKokyaku : refKokuakuList) {
				// 顧客付随管理会社情報の取得
				RcpMKokyakuFKanri fKanri =
					kokyakuFKanriDao.selectByPrimaryKey(refKokyaku.getKokyakuId());
				if (fKanri != null) {
					// ログインＩＤと同じ値かをチェック
					if (user.getLoginId().equals(fKanri.getTaioMailAddress1())) {
						// 対応報告メールアドレス１
						duplicateContentList.add(new MessageBean(
								"MSG0016", "顧客付随情報【不動産・管理会社情報】",
								"対応報告メールアドレス１"));
					}
					if (user.getLoginId().equals(fKanri.getTaioMailAddress2())) {
						// 対応報告メールアドレス２
						duplicateContentList.add(new MessageBean(
								"MSG0016", "顧客付随情報【不動産・管理会社情報】",
								"対応報告メールアドレス２"));
					}
					if (user.getLoginId().equals(fKanri.getTaioMailAddress3())) {
						// 対応報告メールアドレス３
						duplicateContentList.add(new MessageBean(
								"MSG0016", "顧客付随情報【不動産・管理会社情報】",
								"対応報告メールアドレス３"));
					}
				}
			}
			model.setDuplicateContetErrorMessageList(duplicateContentList);

		} else if (user.isConstractor()) {
			// 権限が依頼業者の場合は、業者情報の取得
			RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(user.getGyoshaCd());
			if (gyosha == null) {
				throw new ApplicationException("業者マスタ情報が存在しません。");
			}

			user.setGyoshaNm(gyosha.getGyoshaNm());

			// 重複項目リストの作成（チェックも兼ねる）
			model.setDuplicateContetErrorMessageList(createDuplicateContentList(user));

		} else if (user.isOutsourcerSv() || user.isOutsourcerOp()) {
			// 権限が委託会社SVもしくは委託会社OPの場合は、会社情報の取得
			TbMKaisha kaisha = kaishaDao.selectByPrimaryKey(user.getKaishaId());
			if (kaisha == null) {
				throw new ApplicationException("会社マスタ情報が存在しません。");
			}

			user.setKaishaNm(kaisha.getKaishaNm());
		}

		// 和名変換処理
		// ユーザーマスタ検索用リストの作成
		List<String> loginIdList = new ArrayList<String>();
		loginIdList.add(user.getCreId());
		loginIdList.add(user.getLastUpdId());

		// 和名変換Map取得
		Map<String, String> userMap = userDao.selectByListAsMap(loginIdList);

		// 登録者ＩＤ
		user.setCreUserNm(userMap.containsKey(user.getCreId()) ? userMap.get(user.getCreId()) : user.getCreId());
		// 最終更新者ＩＤ
		user.setLastUpdUserNm(userMap.containsKey(user.getLastUpdId()) ? userMap.get(user.getLastUpdId()) : user.getLastUpdId());

		model.setUser(user);

		return model;
	}

	/**
	 * ユーザー情報登録処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	public void insertUserInfo(TB102UserMasterEntryModel model) {
		// 登録チェック処理
		validateEntryUserInfo(model);

		// ユーザーマスタへの登録値生成
		TbMUser user = createEntryUserInfo(model);

		// ユーザーマスタへの登録処理
		userDao.insert(user);

		model.setSeqNo(user.getSeqNo());
		model.setUser(user);

		if (model.getUser().isInhouse()) {
			// 権限が「TOKAI管理者」または、「TOKAI一般」の場合
			insertGrpKokyaku(model);
		} else if (model.getUser().isRealEstate()) {
			// 参照顧客マスタ情報の削除処理
			refKokyakuDao.deleteBy(user.getKokyakuId(), null);
			// 権限が「管理会社」の場合
			insertRefKokyaku(model);
		}

		// アクセスログへの登録処理
		tbAccesslogDao.insert(TB102UserMasterEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_INSERT, createKensakuJoken(model));

		// 暫定パスワードをメール通知するがチェックONの場合、メール送信を行う
		if (model.isMailSend()) {
			// ユーザー登録完了メールの送信
			sendMail(model, MAIL_KBN_ENTRY);
		}
	}

	/**
	 * ユーザー情報更新処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	public void updateUserInfo(TB102UserMasterEntryModel model) {
		// 登録チェック処理
		validateEntryUserInfo(model);

		// ユーザーマスタへの更新処理
		TbMUser user = model.getUser();
		user.setSeqNo(model.getSeqNo());
		// 権限が「TOKAI管理者」または、「TOKAI一般」の場合
		if (model.getUser().isInhouse()) {
			// 会社IDをTOKAIで設定
			user.setKaishaId(
				((TLCSSB2BUserContext)getUserContext()).
					getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_TOKAI_KAISHA_ID));
		}

		userDao.update(user);

		// 参照顧客マスタ情報の削除処理
		refKokyakuDao.deleteBy(model.getUser().getKokyakuId(), null);
		// グループ顧客マスタ情報の削除処理
		grpKokyakuDao.deleteBy(model.getSeqNo(), null);

		if (model.getUser().isInhouse()) {
			// 権限が「TOKAI管理者」または、「TOKAI一般」の場合
			// グループ顧客マスタ情報の登録処理
			insertGrpKokyaku(model);
		} else if (model.getUser().isRealEstate()) {
			// 権限が「管理会社」の場合
			// 参照顧客マスタ情報の登録処理
			insertRefKokyaku(model);
		}

		// アクセスログへの登録処理
		tbAccesslogDao.insert(TB102UserMasterEntryModel.GAMEN_NM,
				Constants.BUTTON_NM_UPDATE, createKensakuJoken(model));
	}

	/**
	 * パスワード再発行処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	public void reissuePasswordInfo(TB102UserMasterEntryModel model) {
		// ユーザーマスタへの更新値を生成
		TbMUser user = createReissuePasswordInfo(model);

		// ユーザーマスタへの更新処理
		userDao.updateForTempPasswordChange(user);

		// アクセスログへの登録処理
		tbAccesslogDao.insert(TB102UserMasterEntryModel.GAMEN_NM,
				TB102UserMasterEntryModel.BUTTON_NM_REISSUE, createKensakuJoken(model));

		// 暫定パスワードをメール通知するがチェックONの場合、メール送信を行う
		if (model.isMailSend()) {
			// パスワード再発行メールの送信
			sendMail(model, MAIL_KBN_PASSWORD_REISSUE);
		}
	}

	/**
	 * 参照顧客を設定します。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	public TB102UserMasterEntryModel setRefKokyaku(TB102UserMasterEntryModel model) {
		// 初期情報取得処理
		getInitInfo(model);

		// 参照顧客設定
		model.setKokyakuNodeList(getRefKokyaku(model.getUser().getKokyakuId(), false));

		// 画面表示用の顧客ＩＤリストの内容を設定します
		String[] tokaiKokyakuListTexts = model.getTokaiKokyakuListTexts().replaceAll(" ","").split(",");
		if (tokaiKokyakuListTexts != null && !"".equals(tokaiKokyakuListTexts[0])) {
			List<RcpMKokyaku> kokyakuList = new ArrayList<RcpMKokyaku>();
			for (String tokaiKokyakuListText:tokaiKokyakuListTexts) {
				String kokyakuID = tokaiKokyakuListText.substring(0,10);
				kokyakuList.add(kokyakuDao.selectByPrimaryKey(kokyakuID));
			}
			model.setTokaiKokyakuList(kokyakuList);
		}

		return model;
	}

	/**
	 * ユーザのログインＩＤに対する、重複項目リストを生成します。
	 *
	 * @param user ユーザー情報
	 * @return 重複項目リスト
	 */
	private List<MessageBean> createDuplicateContentList(TbMUser user) {
		List<MessageBean> duplicateContentList = new ArrayList<MessageBean>();
		if (StringUtils.isNotBlank(user.getGyoshaCd())) {
			// 業者コードがNULL以外の場合は、業者マスタ情報の取得＆チェック
			RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(user.getGyoshaCd());

			if (gyosha != null &&
				RcpMGyosha.RCP_M_GYOSHA_IRAI_MAIL_ATESAKI_FLG_TRUE.equals(
						gyosha.getIraiMailAtesakiFlg())) {
				// 作業依頼メール送付有無が「1:自動送付する」の場合のみ、チェック
				if (RcpMGyosha.RCP_M_GYOSHA_IRAI_MAIL_ATESAKI_KBN_SAME.equals(
						gyosha.getIraiMailAtesakiKbn())) {
					// 作業依頼メール宛先区分が「1:作業用TELと同じ」場合、作業用メールアドレスをチェック
					if (user.getLoginId().equals(gyosha.getSagyoMailAddress())) {
						// 作業用メールアドレス
						duplicateContentList.add(new MessageBean(
								"MSG0016", "作業依頼メール",
								"作業用メールアドレス"));
					}
				} else if (RcpMGyosha.RCP_M_GYOSHA_IRAI_MAIL_ATESAKI_KBN_DIFF.equals(
						gyosha.getIraiMailAtesakiKbn())) {
					// 作業依頼メール宛先区分が「2:異なる」場合、作業依頼メール宛先メールアドレスをチェック
					if (user.getLoginId().equals(gyosha.getIraiMailAtesakiAddress())) {
						// 作業依頼メール宛先メールアドレス
						duplicateContentList.add(new MessageBean(
								"MSG0016", "作業依頼メール",
								"作業依頼メール宛先メールアドレス"));
					}
				}
			}
		}

		return duplicateContentList;
	}

	/**
	 * ユーザー情報の登録チェック処理を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 */
	private void validateEntryUserInfo(TB102UserMasterEntryModel model) {
		if (model.isInsert()) {
			// アクションタイプが新規登録の場合のみ実施
			// ログインＩＤの重複チェック
			TbMUser user = userDao.selectByLoginId(model.getUser().getLoginId());
			if (user != null) {
				throw new ValidationException(new ValidationPack().addError("MSG0014"));
			}
		}
	}

	/**
	 * 登録するユーザーマスタ情報を生成します。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @return ユーザーマスタ情報
	 */
	private TbMUser createEntryUserInfo(TB102UserMasterEntryModel model) {
		// 連番の取得
		BigDecimal newSeqNo = userDao.nextVal();

		// 暫定パスワード発行処理
		String newTempPassword = PasswordGenerator.generate(TbMUser.TEMP_PASSWORD_LENGTH);

		// 暫定パスワードのハッシュ化
		String hashTempPassword = EncryptionUtil.encrypt(newTempPassword);

		// パスワード期限日
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		// 暫定パスワード有効日数を取得
		int tempPasswordValidDate = userContext.getSystgemContstantAsInt(
				RcpMSystem.RCP_M_SYSTEM_B2B_TEMP_PASSWORD_VALID_DATE);

		Calendar calendar = Calendar.getInstance();
		// システム日付＋暫定パスワード有効日数
		calendar.add(Calendar.DATE, tempPasswordValidDate);

		TbMUser user = model.getUser();

		// 登録する値のセット
		user.setSeqNo(newSeqNo);
		user.setPasswd(hashTempPassword);
		user.setTmpPasswd(newTempPassword);
		user.setPasswdUpdDt(DateUtil.getSysDateTime());
		user.setPasswdKigenDt(DateUtil.toTimestamp(DateUtil.formatTimestamp(DateUtil.toSqlTimestamp(calendar.getTime()), "yyyy/MM/dd"), "yyyy/MM/dd"));
		user.setTmpPasswdFlg(TbMUser.TMP_PASSWD_FLG_TEMP_PASSWORD);
		
		// 権限が「TOKAI管理者」または、「TOKAI一般」の場合
		if (model.getUser().isInhouse()) {
			// 会社IDをTOKAIで設定
			user.setKaishaId(
				userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_TOKAI_KAISHA_ID));
		}

		return user;
	}

	/**
	 * アクセスログに登録する検索条件を生成します。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @return アクセスログに登録する検索条件
	 */
	private String createKensakuJoken(TB102UserMasterEntryModel model) {
		NullExclusionToStringBuilder entryContents =
			new NullExclusionToStringBuilder(
				model.getUser(),
				NullExclusionToStringBuilder.CSV_STYLE, null, null,
				false, false);

		// 除外する項目
		entryContents.setExcludeFieldNames(Constants.EXCLUDE_FIELD_NAMES);

		return entryContents.toString();
	}

	/**
	 * パスワード再発行をするユーザー情報を生成します。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @return ユーザーマスタ情報
	 */
	private TbMUser createReissuePasswordInfo(TB102UserMasterEntryModel model) {
		// 暫定パスワード発行処理
		String newTempPassword = PasswordGenerator.generate(TbMUser.TEMP_PASSWORD_LENGTH);

		// 暫定パスワードのハッシュ化
		String hashTempPassword = EncryptionUtil.encrypt(newTempPassword);

		TbMUser user = model.getUser();

		// 登録する値のセット
		user.setSeqNo(model.getSeqNo());
		user.setPasswd(hashTempPassword);
		user.setTmpPasswd(newTempPassword);

		return user;
	}

	/**
	 * メール送信を行います。
	 *
	 * @param model ユーザーマスタ登録画面モデル
	 * @param mailKbn メール区分(1:ユーザー登録完了メール、2:パスワード再発行メール)
	 */
	private void sendMail(TB102UserMasterEntryModel model, int mailKbn) {
		try {
			// 画面で入力したユーザー情報
			TbMUser user = model.getUser();
			// パスワード再発行メールの場合、
			// ユーザー名などは更新されないため、ＤＢに登録されているユーザー情報を使用する
			TbMUser dbUser = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					userDao.selectByPrimaryKey(model.getSeqNo()) : null;

			// テンプレートファイル名
			String templateFileNm = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					MailTemplate.PASSWORD_REISSUE_MAIL_FILE_NAME :
					MailTemplate.USER_ENTRY_MAIL_FILE_NAME;

			// 置換文字列Map生成
			VelocityWrapper wrapper = new VelocityWrapper(
					templateFileNm);

			TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();

			// 不動産・管理会社名、業者名、会社名用エンティティ取得
			RcpMKokyaku kokyakuEntity = new RcpMKokyaku();
			RcpMGyosha gyoshaEntitiy = new RcpMGyosha();
			TbMKaisha kaishaEntity = new TbMKaisha();
			if (StringUtils.isNotBlank(user.getKokyakuId())) {
				kokyakuEntity = kokyakuDao.selectByPrimaryKey(user.getKokyakuId());
			}
			if (StringUtils.isNotBlank(user.getGyoshaCd())) {
				gyoshaEntitiy = gyoshaDao.selectByPrimaryKey(user.getGyoshaCd());
			}
			if (StringUtils.isNotBlank(user.getKaishaId())) {
				kaishaEntity = kaishaDao.selectByPrimaryKey(user.getKaishaId());
			}

			// ユーザー名
			// パスワード再発行メールの場合、ＤＢに登録されているユーザー名を表示
			String userNm = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					dbUser.getUserNm() : user.getUserNm();
			if (StringUtils.isNotBlank(user.getKokyakuId())) {
				// 顧客ＩＤがnull以外の場合、ユーザー名に不動産・管理会社名を設定
				userNm = kokyakuEntity.getKanjiNm();
			}
			if (StringUtils.isNotBlank(user.getGyoshaCd())) {
				// 業者コードがnull以外の場合、ユーザー名に業者名を設定
				userNm = gyoshaEntitiy.getGyoshaNm();
			}
			if (StringUtils.isNotBlank(user.getKaishaId())) {
				// 会社IDがnull以外の場合、ユーザー名に会社名を設定
				userNm = kaishaEntity.getKaishaNm();
			}
			// システム名
			String systemName = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_NAME);
			// システムのURL
			String systemUrl = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_URL);
			// システム問い合わせ先電話番号
			String systemTelNo = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SYSTEM_TOIAWASE_TEL_NO);
			// 返信メールアドレス
			String returnMailAddress = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_RETURN_MAIL_ADDRESS);
			// サポートセンター受付時間
			String supportReceptionTime = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_SUPPORT_RECEPTION_TIME);

			// メール件名
			String subject = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					userContext.getSystgemContstantAsString(
							RcpMSystem.RCP_M_SYSTEM_B2B_SUBJECT_PASSWORD_REISUUE) :
					userContext.getSystgemContstantAsString(
							RcpMSystem.RCP_M_SYSTEM_B2B_SUBJECT_USER_ENTRY_COMPLATE);

			// 置換文字列
			// ユーザー名
			wrapper.put(MAIL_KEY_USER_NAME, userNm);
			// システム名
			wrapper.put(MAIL_KEY_SYSTEM_NAME, systemName);
			// 発行した暫定パスワード
			wrapper.put(MAIL_KEY_TEMP_PASSWORD, user.getTmpPasswd());
			// システムのURL
			wrapper.put(MAIL_KEY_SYSTEM_URL, systemUrl);
			// システム問い合わせ先電話番号
			wrapper.put(MAIL_KEY_CONTACT_INFORMAITION, systemTelNo);
			// 返信メールアドレス
			wrapper.put(MAIL_KEY_RETURN_MAIL_ADDRESS, returnMailAddress);
			// サポートセンター受付時間
			wrapper.put(MAIL_KEY_SUPPORT_RECEPTION_TIME, supportReceptionTime);

			// パスワード再発行の場合は、ＤＢのパスワード期限日を使用
			Timestamp passwdKigenDt = (mailKbn == MAIL_KBN_PASSWORD_REISSUE) ?
					dbUser.getPasswdKigenDt() : user.getPasswdKigenDt();
			// パスワード期限日
			wrapper.put(MAIL_KEY_PASSWD_KIGEN_DT,
					DateUtil.formatTimestamp(passwdKigenDt, "yyyy/MM/dd"));
			// パスワード期限日の曜日
			wrapper.put(MAIL_KEY_DAY_OF_WEEK,
					DateUtil.getDayOfWeek(passwdKigenDt));

			VelocityEmail email = new VelocityEmail();
			// 差出元アドレス
			email.setFrom(userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ADDRESS));
			// 認証
			email.setAuthenticator(new DefaultAuthenticator(
					userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_ACCOUNT),
					userContext.getSystgemContstantAsString(RcpMSystem.RCP_M_SYSTEM_B2B_FROM_MAIL_PASSWORD)));
			// 宛先
			email.addTo(user.getLoginId());
			// 宛先BCC
			String bcc = userContext.getSystgemContstantAsString(
					RcpMSystem.RCP_M_SYSTEM_B2B_BCC_ADDRESS);
			if (StringUtils.isNotBlank(bcc)) {
				email.addBcc(StringUtils.split(bcc, ","));
			}
			// 返信メールアドレス
			email.addReplyTo(returnMailAddress);
			// 件名
			email.setSubject(subject);
			// 置換文字列Mapの設定
			email.setVelocityWrapper(wrapper);

			// メール送信
			email.send();
		} catch (EmailException e) {
			// メール送信に失敗した場合
			throw new ApplicationException("メール送信に失敗しました。", e);
		} catch (Exception e) {
			// その他例外
			throw new ApplicationException(e);
		}
	}

	/**
	 * グループ顧客マスタ（ＴＯＫＡＩ用）の登録を行います。
	 *
	 * @param model
	 */
	private void insertGrpKokyaku(TB102UserMasterEntryModel model) {
		// 登録処理
		String[] tokaiKokyakuListTexts = model.getTokaiKokyakuListTexts().replaceAll(" ","").split(",");
		BigDecimal hyojiJun = BigDecimal.ZERO;
		for (String tokaiKokyakuListText:tokaiKokyakuListTexts) {
			TbMGrpKokyaku grpKokyaku = new TbMGrpKokyaku();
			hyojiJun = hyojiJun.add(BigDecimal.ONE);
			// 登録する値のセット
			grpKokyaku.setSeqNo(model.getSeqNo());
			grpKokyaku.setRefKokyakuId(tokaiKokyakuListText.substring(0,10));
			grpKokyaku.setHyojiJun(hyojiJun);
			// 登録処理
			grpKokyakuDao.insert(grpKokyaku);
		}
		return;
	}

	/**
	 * 参照顧客マスタの登録を行います。
	 *
	 * @param model
	 */
	private void insertRefKokyaku(TB102UserMasterEntryModel model) {
		List<RC02BKokyakuNodeDto> kokyakuNodeList = model.getKokyakuNodeList();
		BigDecimal hyojiJun = BigDecimal.ZERO;
		// 登録処理
		for(int i=0; i < kokyakuNodeList.size(); i++) {
			RC02BKokyakuNodeDto kokyakuNode = kokyakuNodeList.get(i);
			// 登録フラグ「1:登録可能」の場合は登録実行
			// ※登録フラグは画面操作で設定（画面の活性項目が登録対象）
			if (StringUtils.isNotBlank(kokyakuNode.getEntryFlg())
					&& RC02BKokyakuNodeDto.ENTRY_OK.equals(kokyakuNode.getEntryFlg())) {
				TbMRefKokyaku refKokyaku = new TbMRefKokyaku();
				hyojiJun = hyojiJun.add(BigDecimal.ONE);
				// 登録する値のセット
				refKokyaku.setRefKokyakuId(model.getUser().getKokyakuId());
				refKokyaku.setKokyakuId(kokyakuNode.getKokyakuId());
				refKokyaku.setKaisoLevel(kokyakuNode.getKaisoLevel());
				refKokyaku.setLowerDspFlg(StringUtils.isNotBlank(kokyakuNode.getHyojiFlg()) ? kokyakuNode.getHyojiFlg() : RC02BKokyakuNodeDto.HYOJI_OFF);
				refKokyaku.setLeafFlg(kokyakuNode.getIsLeaf());
				refKokyaku.setHyojiJun(hyojiJun);
				// 登録実行
				refKokyakuDao.insert(refKokyaku);
			}
		}

		return;
	}

	/**
	 * 顧客階層DTOリストを取得します。
	 *
	 * @param rootKokyakuId	参照顧客ID
	 * @param isUpdateInit	更新初期表示フラグ
	 *
	 * @return List<RC02BKokyakuNodeDto> 顧客階層DTOリスト
	 */
	private List<RC02BKokyakuNodeDto> getRefKokyaku(String rootKokyakuId, boolean isUpdateInit) {

		// 顧客階層DTOリスト取得
		List<RC02BKokyakuNodeDto> kokyakuNodeList =
			kokyakuKanrenDao.selectNodeList(rootKokyakuId, RcpMKokyaku.KOKYAKU_KBN_FUDOSAN);
		// 参照顧客情報取得
		List<TbMRefKokyaku> refKokuakuList = refKokyakuDao.selectBy(rootKokyakuId, null);
		boolean newDataFlg = (refKokuakuList == null || refKokuakuList.isEmpty());

		for (RC02BKokyakuNodeDto kokyakuNode : kokyakuNodeList) {
			// 表示フラグ初期設定（参照顧客情報が存在しない：「1:表示」、存在する：「0:非表示」（後で変更あり））
			kokyakuNode.setHyojiFlg((newDataFlg) ? RC02BKokyakuNodeDto.HYOJI_ON : RC02BKokyakuNodeDto.HYOJI_OFF);
			// 背景色設定（更新初期表示時のみ、灰色設定をtrueで設定）
			kokyakuNode.setDisplayGray(isUpdateInit);
			for (TbMRefKokyaku refKokuaku : refKokuakuList) {
				if (kokyakuNode.getKokyakuId().toString().equals(refKokuaku.getKokyakuId().toString())){
					// 顧客階層DTOの顧客IDが参照顧客情報に存在する場合
					kokyakuNode.setDisplayGray(false);
					if(RC02BKokyakuNodeDto.HYOJI_ON.equals(refKokuaku.getLowerDspFlg())) {
						// 表示フラグ設定
						// 顧客階層DTOの顧客IDが参照顧客情報に存在し、かつ参照情報が「表示ON」の場合、「1:表示」設定
						kokyakuNode.setHyojiFlg(RC02BKokyakuNodeDto.HYOJI_ON);
					}
				}
			}
		}

		return kokyakuNodeList;
	}
}
