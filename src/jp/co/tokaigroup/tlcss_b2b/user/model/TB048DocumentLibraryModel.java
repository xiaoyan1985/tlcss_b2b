package jp.co.tokaigroup.tlcss_b2b.user.model;

import java.util.List;

import jp.co.tokaigroup.reception.common.model.RC000CommonModel;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.TbTPublishFile;

/**
 * �������C�u�����ꗗ���f���B
 *
 * @author C.Kobayashi
 * @version 1.0 2015/10/28
 */
public class TB048DocumentLibraryModel extends RC000CommonModel {
	
	/** �ڋq�h�c */
	private String kokyakuId;

	/** ���[�U�[��`�̃t�@�C���� */
	private String userFileNm;
	/** ���t�@�C���� */
	private String realFileNm;
	
	/** �������C�u�������X�g */
	private List<TbTPublishFile> publishFileList;

	/** �ڋq��� */
	private RcpMKokyaku kokyaku;
	
	/** �������C�u�������X�g�̃��X�g�� */
	private int listSize;
	
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
	 * ���[�U�[��`�̃t�@�C�������擾���܂��B
	 *
	 * @return userFileNm
	 */
	public String getUserFileNm() {
		return userFileNm;
	}
	/**
	 * ���[�U�[��`�̃t�@�C������ݒ肵�܂��B
	 *
	 * @param userFileNm
	 */
	public void setUserFileNm(String userFileNm) {
		this.userFileNm = userFileNm;
	}

	/**
	 * ���t�@�C�������擾���܂��B
	 *
	 * @return realFileNm
	 */
	public String getRealFileNm() {
		return realFileNm;
	}
	/**
	 * ���t�@�C������ݒ肵�܂��B
	 *
	 * @param realFileNm
	 */
	public void setRealFileNm(String realFileNm) {
		this.realFileNm = realFileNm;
	}

	/**
	 * �������C�u�������X�g���擾���܂��B
	 *
	 * @return �������C�u�������X�g
	 */
	public List<TbTPublishFile> getPublishFileList() {
		return publishFileList;
	}
	/**
	 * �������C�u�������X�g��ݒ肵�܂��B
	 *
	 * @param publishFileList �������C�u�������X�g
	 */
	public void setPublishFileList(List<TbTPublishFile> publishFileList) {
		this.publishFileList = publishFileList;
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
	 * �������C�u�����\�����̃T�C�Y���擾���܂��B
	 * @return �������C�u�����\�����̃T�C�Y
	 */
	public int getListSize() {
		return listSize;
	}
	/**
	 * �������C�u�����\�����̃T�C�Y��ݒ肵�܂��B
	 * @param listSize �������C�u�����\�����̃T�C�Y
	 */
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

}
