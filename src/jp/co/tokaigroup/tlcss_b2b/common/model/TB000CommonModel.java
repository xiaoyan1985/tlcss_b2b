package jp.co.tokaigroup.tlcss_b2b.common.model;

import jp.co.tokaigroup.reception.common.Constants;

import org.apache.commons.lang.StringUtils;

/**
 * �S��ʋ��ʃ��f���B
 *
 * @author k002849
 * @version 1.0 2014/04/22
 */
public class TB000CommonModel {
	/** �A�N�V�����^�C�v */
	private String actionType;

	/**
	 * �A�N�V�����^�C�v���擾���܂��B
	 *
	 * @return �A�N�V�����^�C�v
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 * �A�N�V�����^�C�v��ݒ肵�܂��B
	 *
	 * @param actionType �A�N�V�����^�C�v
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * �A�N�V�����^�C�v���V�K�o�^���[�h�Ȃ̂��𔻒肵�܂��B
	 *
	 * @return true:�V�K�o�^���[�h�Afalse:����ȊO
	 */
	public boolean isInsert() {
		if (StringUtils.isBlank(this.actionType)) {
			return true;
		}

		return (Constants.ACTION_TYPE_INSERT.equals(this.actionType));
	}

	/**
	 * �A�N�V�����^�C�v���X�V���[�h�Ȃ̂��𔻒肵�܂��B
	 *
	 * @return true:�X�V���[�h�Afalse:����ȊO
	 */
	public boolean isUpdate() {
		return (Constants.ACTION_TYPE_UPDATE.equals(this.actionType));
	}

	/**
	 * �A�N�V�����^�C�v���폜���[�h�Ȃ̂��𔻒肵�܂��B
	 *
	 * @return true:�폜���[�h�Afalse:����ȊO
	 */
	public boolean isDelete() {
		return (Constants.ACTION_TYPE_DELETE.equals(this.actionType));
	}
}
