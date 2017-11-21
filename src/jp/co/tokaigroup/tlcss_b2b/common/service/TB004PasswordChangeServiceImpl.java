package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.reception.dao.TbMUserDao;
import jp.co.tokaigroup.reception.dao.TbTAccesslogDao;
import jp.co.tokaigroup.reception.entity.TbMUser;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.si.fw.util.EncryptionUtil;
import jp.co.tokaigroup.si.fw.util.StringUtil;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB004PasswordChangeModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * パスワード変更サービス実装クラス。
 *
 * @author k002849
 * @version 4.1 2014/06/09
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB004PasswordChangeServiceImpl extends TLCSSB2BBaseService implements TB004PasswordChangeService {
	/** パスワード変更時のアクセスログの検索条件 */
	private static final String INSERT_ACCESS_LOG_KENSAKU_JOKEN = "-";

	/** 外部サイトシステム ユーザーマスタDAO */
	@Autowired
	private TbMUserDao userDao;

	/** 外部サイトシステム 外部サイトアクセスログDAO */
	@Autowired
	private TbTAccesslogDao tbAccesslogDao;

	/**
	 * パスワード更新処理を行います。
	 *
	 * @param model パスワード変更画面モデル
	 */
	@Transactional(value="txManager")
	public void updatePassword(TB004PasswordChangeModel model) {
		// パスワードの妥当性チェック
		validatePassword(model);

		// 新パスワードをハッシュ化
		String hashPassword = EncryptionUtil.encrypt(model.getNewPassword());

		// ユーザーマスタの更新
		userDao.updateForPasswordChange(createUserInfoForPasswordChange(hashPassword));

		// 外部サイトアクセスログの登録
		tbAccesslogDao.insert(TB004PasswordChangeModel.GAMEN_NM,
				Constants.BUTTON_NM_CHANGE, INSERT_ACCESS_LOG_KENSAKU_JOKEN);
	}

	/**
	 * パスワードの妥当性チェックを行います。
	 *
	 * @param model パスワード変更画面モデル
	 */
	private void validatePassword(TB004PasswordChangeModel model) {
		// 現在のパスワード入力チェック
		TbMUser user = userDao.selectByLoginId(getUserContext().getLoginId());

		// 現在のパスワードをハッシュ化
		String hashPassword = EncryptionUtil.encrypt(model.getCurrentPassword());
		if (!user.getPasswd().equals(hashPassword)) {
			// パスワードが間違っていた場合
			throw new ValidationException(new ValidationPack().addError("MSG0013"));
		}
		//新パスワードがログインIDと同じ場合
		if (model.getNewPassword().equals(getUserContext().getLoginId())) {
			throw new ValidationException(new ValidationPack().addError("MSG0018"));
		}
		//新パスワードが、英字、数字、記号から2種類以上、含まれているかを判定
		if (StringUtil.countCharacterType(model.getNewPassword()) < 2){
			//新しいパスワードの入力チェックがNGの場合
			throw new ValidationException(new ValidationPack().addError("MSG0017"));
		}
	}

	/**
	 * パスワード変更を行うためのユーザーマスタ情報を生成します。
	 *
	 * @param newPassword 新パスワード(ハッシュ化済)
	 * @return ユーザーマスタ情報
	 */
	private TbMUser createUserInfoForPasswordChange(String newPassword) {
		TbMUser user = new TbMUser();

		user.setLoginId(getUserContext().getLoginId());
		user.setPasswd(newPassword);

		return user;
	}

}
