package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.common.model.RC000CommonModel;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuManual;
import jp.co.tokaigroup.tlcss_b2b.dto.TB027CommonManualListDto;

/**
 * �ڋq�}�j���A���ꗗ��ʃ��f���B
 *
 * @author k002849
 * @version 1.0 2014/03/20
 * @version 1.1 2016/07/07 H.Hirai ����������Ή�
 */
public class TB027CustomerManualModel extends RC000CommonModel {
	/** �ڋq�h�c */
	private String kokyakuId;
	/** �A�b�v���[�h�t�@�C���� */
	private String uploadFileNm;

	/** �Ώیڋq�h�c */
	private String targetKokyakuId;

	/** �ʃ}�j���A�����X�g */
	private List<RcpTKokyakuManual> kobetsuManualList;
	/** ���ʃ}�j���A���c�s�n���X�g */
	private List<TB027CommonManualListDto> commonManualDtoList;

	/** �ڋq��� */
	private RcpMKokyaku kokyaku;
	/** ������ڋq��� */
	private List<RcpMKokyaku> seikyusakiKokyakuList;

	/**
	 * �ڋq�h�c���擾���܂��B
	 *
	 * @return �ڋq�h�c
	 */
	public String getKokyakuId() {
		return kokyakuId;
	}
	/**
	 * �ڋq�h�c��ݒ肵�܂��B
	 *
	 * @param kokyakuId �ڋq�h�c
	 */
	public void setKokyakuId(String kokyakuId) {
		this.kokyakuId = kokyakuId;
	}

	/**
	 * �A�b�v���[�h�t�@�C�������擾���܂��B
	 *
	 * @return �A�b�v���[�h�t�@�C����
	 */
	public String getUploadFileNm() {
		return uploadFileNm;
	}
	/**
	 * �A�b�v���[�h�t�@�C������ݒ肵�܂��B
	 *
	 * @param uploadFileNm �A�b�v���[�h�t�@�C����
	 */
	public void setUploadFileNm(String uploadFileNm) {
		this.uploadFileNm = uploadFileNm;
	}

	/**
	 * �Ώیڋq�h�c���擾���܂��B
	 *
	 * @return �Ώیڋq�h�c
	 */
	public String getTargetKokyakuId() {
		return targetKokyakuId;
	}

	/**
	 * �Ώیڋq�h�c��ݒ肵�܂��B
	 *
	 * @param targetKokyakuId �Ώیڋq�h�c
	 */
	public void setTargetKokyakuId(String targetKokyakuId) {
		this.targetKokyakuId = targetKokyakuId;
	}

	/**
	 * �ʃ}�j���A�����X�g���擾���܂��B
	 *
	 * @return �ʃ}�j���A�����X�g
	 */
	public List<RcpTKokyakuManual> getKobetsuManualList() {
		return kobetsuManualList;
	}
	/**
	 * �ʃ}�j���A�����X�g��ݒ肵�܂��B
	 *
	 * @param kobetsuManualList �ʃ}�j���A�����X�g
	 */
	public void setKobetsuManualList(List<RcpTKokyakuManual> kobetsuManualList) {
		this.kobetsuManualList = kobetsuManualList;
	}

	/**
	 * ���ʃ}�j���A���c�s�n���X�g���擾���܂��B
	 * 
	 * @return ���ʃ}�j���A���c�s�n���X�g
	 */
	public List<TB027CommonManualListDto> getCommonManualDtoList() {
		return commonManualDtoList;
	}

	/**
	 * ���ʃ}�j���A���c�s�n���X�g��ݒ肵�܂��B
	 * 
	 * @param commonManualDtoList ���ʃ}�j���A���c�s�n���X�g
	 */
	public void setCommonManualDtoList(
			List<TB027CommonManualListDto> commonManualDtoList) {
		this.commonManualDtoList = commonManualDtoList;
	}

	/**
	 * �ڋq�����擾���܂��B
	 *
	 * @return �ڋq���
	 */
	public RcpMKokyaku getKokyaku() {
		return kokyaku;
	}
	/**
	 * �ڋq����ݒ肵�܂��B
	 *
	 * @param kokyaku �ڋq���
	 */
	public void setKokyaku(RcpMKokyaku kokyaku) {
		this.kokyaku = kokyaku;
	}

	/**
	 * ������ڋq�����擾���܂��B
	 * 
	 * @return ������ڋq���
	 */
	public List<RcpMKokyaku> getSeikyusakiKokyakuList() {
		return seikyusakiKokyakuList;
	}

	/**
	 * ������ڋq����ݒ肵�܂��B
	 * 
	 * @param seikyusakiKokyakuList ������ڋq���
	 */
	public void setSeikyusakiKokyakuList(List<RcpMKokyaku> seikyusakiKokyakuList) {
		this.seikyusakiKokyakuList = seikyusakiKokyakuList;
	}

	/**
	 * �ڋq�}�j���A�����X�g�̃f�[�^�������擾���܂��B
	 *
	 * @return �ڋq�}�j���A�����X�g�̃f�[�^����
	 */
	public int getKokyakuManualListSize() {
		if (this.kobetsuManualList == null) {
			return 0;
		}

		return this.kobetsuManualList.size();
	}

	/**
	 * ���ʃ}�j���A����\�����邩�𔻒肵�܂��B
	 *
	 * @return true:�\������Afalse:�\�����Ȃ�
	 */
	public boolean isCommonManualDisplay() {
		// ������ڋq���̌ڋq�h�c���p�����[�^�̌ڋq�h�c�ƈقȂ�ꍇ�́A�\��
		boolean ret = false;
		for (RcpMKokyaku kokyakuInfo : this.seikyusakiKokyakuList) {
			if (!kokyakuInfo.getKokyakuId().equals(this.kokyakuId)) {
				ret = true;
				break;
			}
		}
		return ret;
	}
}
