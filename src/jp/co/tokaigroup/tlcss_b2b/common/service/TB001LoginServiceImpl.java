package jp.co.tokaigroup.tlcss_b2b.common.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.tokaigroup.reception.dao.RcpMKokyakuDao;
import jp.co.tokaigroup.reception.dao.RcpMSystemDao;
import jp.co.tokaigroup.reception.dao.TbMRefKokyakuDao;
import jp.co.tokaigroup.reception.dao.TbMRoleUrlDao;
import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbMRoleUrl;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.DateUtil;
import jp.co.tokaigroup.si.fw.util.EncryptionUtil;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB001LoginModel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ログインサービス実装クラス。
 *
 * @author k002849
 * @version 1.0 2014/04/22
 * @version 1.1 2015/08/25 v145527 委託会社ＩＤ追加対応
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB001LoginServiceImpl extends TLCSSB2BBaseService implements TB001LoginService {
	/** 外部サイトシステム ユーザーマスタDAO */
	@Autowired
	private TbMUserDao userDao;

	/** 外部サイトシステム アクセス可能ＵＲＬマスタDAO */
	@Autowired
	private TbMRoleUrlDao roleUrlDao;

	/** リセプション システムマスタDAO */
	@Autowired
	private RcpMSystemDao systemDao;

	/** リセプション グループ顧客マスタ（ＴＯＫＡＩ用） */
	@Autowired
	private TbMRefKokyakuDao refKokyakuDao;

	/** リセプション顧客マスタDAO */
	@Autowired
	private RcpMKokyakuDao kokyakuDao;

	/**
	 * ログイン処理を行います。
	 *
	 * @param model ログイン画面モデル
	 * @return ログイン画面モデル
	 */
	public TB001LoginModel executeLogin(TB001LoginModel model) {
		// システムマスタ全件取得
		List<RcpMSystem> systemList = systemDao.selectAll();

		Map<String, String> systemMap = new HashMap<String, String>();
		// システムマスタの値をMapにする
		for (RcpMSystem system : systemList) {
			systemMap.put(system.getCode(), system.getValue());
		}

		//---------- ログインチェック処理 ここから ----------//
		// ログインＩＤ存在チェック
		TbMUser user = userDao.selectByLoginId(model.getLoginId());
		if (user == null || user.isDeleted()) {
			// ログインＩＤが存在しない、または、削除されている場合
			throw new ValidationException(new ValidationPack().addError("MSG0008"));
		}

		//権限が「20：不動産・管理会社」の場合、ユーザーグルーピング情報を取得する。
		if (user.isRealEstate()) {
			// ユーザーマスタの顧客情報を取得する。
			RcpMKokyaku kokyaku = kokyakuDao.selectByPrimaryKey(user.getKokyakuId());
			if (kokyaku == null || kokyaku.isKeiyakuKanriEndFlgExpired()) {
				// 顧客情報が取得できない、または契約管理終了フラグが「2：終了、保持期間経過」の場合
				throw new ValidationException(new ValidationPack().addError("MSG0029"));
			}
			// 顧客マスタ情報を設定する
			model.setKokyakuInfo(kokyaku);
			// 参照顧客リストを設定します。
			model.setRefKokyakuList(refKokyakuDao.selectBy(user.getKokyakuId(), null));
			if (model.getRefKokyakuList() == null || model.getRefKokyakuList().isEmpty()) {
				// 参照顧客が一件もない場合
				throw new ValidationException(new ValidationPack().addError("MSG0029"));
			}
		}

		// 権限が「10:TOKAI管理会社」、「11:TOKAI一般」、「40:委託会社SV」、「41:委託会社OP」の場合
		if (user.isAdministrativeInhouse()
			|| user.isGeneralInhouse()
			|| user.isOutsourcerSv()
			|| user.isOutsourcerOp()) {
			
			if (StringUtils.isBlank(user.getKaishaId())) {
				// 委託会社ＩＤがNULLの場合
				throw new ValidationException(new ValidationPack().addError("MSG0008"));
			}
		}

		// パスワードチェック
		String hashPassword = EncryptionUtil.encrypt(model.getPasswd());

		if (!user.getPasswd().equals(hashPassword)) {
			// 入力されたパスワードとハッシュ化されたパスワードが一致しない場合
			throw new ValidationException(new ValidationPack().addError("MSG0008"));
		}
		//---------- ログインチェック処理 ここまで ----------//

		// パスワード期限切れのチェック
		// システム日付
		Timestamp sysDate = DateUtil.toTimestamp(
				DateUtil.getSysDateString("yyyy/MM/dd"), "yyyy/MM/dd");
		// パスワード期限終了日
		Timestamp passWdKigenDt = user.getPasswdKigenDt();

		model.setUserInfo(user);
		model.setSystemConstants(systemMap);

		// 残り有効日数の取得
		model.setValidDateCount(NumberUtils.toInt(
				DateUtil.differenceDate(passWdKigenDt, sysDate)));

		// アクセス可能ＵＲＬマスタからアクセス可能ＵＲＬMap取得
		Map<String, TbMRoleUrl> accessibleMap =
			roleUrlDao.selectAccessibleMap(user.getRole());

		model.setAccessibleMap(accessibleMap);

		return model;
	}
}
