package jp.co.tokaigroup.tlcss_b2b.dto;

import java.util.List;

import jp.co.tokaigroup.reception.dto.RC000CommonDto;
import jp.co.tokaigroup.reception.entity.RcpTKokyakuManual;

/**
 * �ڋq���ʃ}�j���A�����c�s�n�B
 *
 * @author H.Hirai
 * @version 1.0 2016/07/07
 * 
 */
public class TB027CommonManualListDto extends RC000CommonDto {

	/** �ڋq�h�c */
	private String kokyakuId;

	/** ���ʃ}�j���A�����X�g */
	private List<RcpTKokyakuManual> commonManualList;

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
	 * ���ʃ}�j���A�����X�g���擾���܂��B
	 * 
	 * @return ���ʃ}�j���A�����X�g
	 */
	public List<RcpTKokyakuManual> getCommonManualList() {
		return commonManualList;
	}

	/**
	 * ���ʃ}�j���A�����X�g��ݒ肵�܂��B
	 * 
	 * @param commonManualList ���ʃ}�j���A�����X�g
	 */
	public void setCommonManualList(List<RcpTKokyakuManual> commonManualList) {
		this.commonManualList = commonManualList;
	}
}
