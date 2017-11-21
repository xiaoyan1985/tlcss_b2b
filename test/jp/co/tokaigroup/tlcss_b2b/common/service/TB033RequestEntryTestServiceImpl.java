package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.common.Constants;
import jp.co.tokaigroup.reception.dao.NatosMPasswordDao;
import jp.co.tokaigroup.reception.dao.RcpMComCdDao;
import jp.co.tokaigroup.reception.dao.RcpMGyoshaDao;
import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpTIraiDao;
import jp.co.tokaigroup.reception.dao.RcpTToiawaseDao;
import jp.co.tokaigroup.reception.dao.SosMUserInfoDao;
import jp.co.tokaigroup.reception.entity.NatosMPassword;
import jp.co.tokaigroup.reception.entity.RcpMComCd;
import jp.co.tokaigroup.reception.entity.RcpMGyosha;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTIrai;
import jp.co.tokaigroup.reception.entity.RcpTToiawase;
import jp.co.tokaigroup.reception.entity.SosMUserInfo;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB033RequestEntryTestModel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(value="txManager", readOnly = true)
@Scope("prototype")
public class TB033RequestEntryTestServiceImpl extends TLCSSB2BBaseService 
		implements TB033RequestEntryTestService {

	/** リセプション共通マスタDAO */
	@Autowired
	private RcpMComCdDao comCdDao;

	/** 依頼情報ＴDAO */
	@Autowired
	private RcpTIraiDao iraiDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/** NATOSパスワードＭDAO */
	@Autowired
	private NatosMPasswordDao natosPasswordDao;

	/** SOSユーザ付随情報ＭDAO */
	@Autowired
	private SosMUserInfoDao sosUserInfoDao;

	/** 問い合わせ情報ＴDAO */
	@Autowired
	private RcpTToiawaseDao toiawaseDao;

	/** 業者マスタDAO */
	@Autowired
	private RcpMGyoshaDao gyoshaDao;



	/**
	 * 初期情報取得処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB033RequestEntryTestModel getInitInfo(
			TB033RequestEntryTestModel model) {
		// 共通コードマスタリストMapの取得
		Map<String, List<RcpMComCd>> comKbnMap = comCdDao.selectAnyAsMap(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI);
		// 共通コードマスタリストMapより訪問予定日リストを取得
		model.setHomonYoteiYmdList(comKbnMap.get(RcpMComCd.RCP_M_COM_CD_KBN_HOMON_YOTEI));

		// ユーザ付随情報マスタ-職務区分指定検索からユーザリストを取得
		List<String> shokumuKbnList = new ArrayList<String>();
		shokumuKbnList.add(Constants.SHOKUMU_KBN_SOS_KANRISHA);
		shokumuKbnList.add(Constants.SHOKUMU_KBN_SV);
		shokumuKbnList.add(Constants.SHOKUMU_KBN_OP);
		List<SosMUserInfo> userInfoList = sosUserInfoDao.selectByShokumuKbnList(shokumuKbnList);
		List<String> loginIdList = new ArrayList<String>();
		if (userInfoList != null && userInfoList.size() != 0) {
			for (SosMUserInfo userInfo : userInfoList) {
				loginIdList.add(userInfo.getUserId());
			}
		} else {
			loginIdList.add("");
		}

		// 発注担当者リスト取得。取得したユーザリストを引数にパスワードマスタEntityリストの取得
		List<NatosMPassword> passwordList = natosPasswordDao.selectByList(loginIdList, model.getIrai().getTantoshaId());
		model.setNatosMPasswordList(passwordList);

		// 問い合わせ情報Entityの取得
		RcpTToiawase  toiawase = toiawaseDao.selectByPrimaryKey(model.getToiawaseNo());
		if (toiawase == null) {
			// 検索結果がnullの場合、以下のエラーメッセージを出力
			throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
		}
		model.setToiawase(toiawase);

		if (StringUtils.isNotBlank(toiawase.getKokyakuId())) {
			// 顧客情報の取得
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(toiawase.getKokyakuId());
			if (kokyaku == null) {
				// 顧客マスタの情報が取得できない場合は、排他エラー
				throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
			}
			model.setKokyaku(kokyaku);
		}

		if (model.isExceptionFlg()) {
			// 例外発生時は、依頼情報を取得しない
			return model;
		}

		// 依頼情報の取得
		if (model.isUpdate()) {
			RcpTIrai irai = iraiDao.selectByPrimaryKey(model.getToiawaseNo(), model.getToiawaseRirekiNo());
			if (irai == null) {
				// 検索結果がnullの場合、以下のエラーメッセージを出力
				throw new ValidationException(new ValidationPack().addError("MSG0003", ""));
			}
			model.setIrai(irai);

			// パスワードマスタ情報(最終更新者ID)の取得
			NatosMPassword natosPassword = natosPasswordDao.selectByPrimaryKey(irai.getLastUpdId());
			if (natosPassword == null) {
				natosPassword = new NatosMPassword();
				natosPassword.setUserNm(irai.getLastUpdId());
			}
			model.setNatosMPassword(natosPassword);

			// パスワードマスタ情報(最終印刷者ID)の取得
			NatosMPassword natosPasswordLastPrintId = natosPasswordDao.selectByPrimaryKey(irai.getLastPrintId());
			if (natosPasswordLastPrintId == null) {
				natosPasswordLastPrintId = new NatosMPassword();
				natosPasswordLastPrintId.setUserNm(irai.getLastUpdId());
			}
			model.setNatosPasswordLastPrintId(natosPasswordLastPrintId);

			// 依頼業者マスタリストの取得
			RcpMGyosha gyosha = gyoshaDao.selectByPrimaryKey(irai.getIraiGyoshaCd());
			model.setGyosha(gyosha);

		}
		return model;
	}

	/**
	 * 依頼情報取得処理を行います。
	 *
	 * @param model 画面モデル
	 * @return 画面モデル
	 */
	public TB033RequestEntryTestModel getIraiInfo(
			TB033RequestEntryTestModel model) {
		return model;
	}

}
