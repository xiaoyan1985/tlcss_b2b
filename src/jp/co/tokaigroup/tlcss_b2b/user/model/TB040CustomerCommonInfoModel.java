package jp.co.tokaigroup.tlcss_b2b.user.model;

import org.apache.commons.lang.StringUtils;

import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.kokyaku.model.RC011KokyakuSearchCondition;
import jp.co.tokaigroup.tlcss_b2b.common.model.TB000CommonModel;
import jp.co.tokaigroup.tlcss_b2b.common.Constants;

/**
 * �ڋq��{��񋤒ʃ��f���B
 *
 * @author v140546
 * @version 1.0 2014/05/29
 * @version 1.1 2015/07/24 v145527
 * @version 1.2 2015/11/04 J.Matsuba ���ӎ����\�����胁�\�b�h�ǉ�
 * 
 */
public class TB040CustomerCommonInfoModel extends TB000CommonModel {
	
	/** �^�C�g���ڔ��� */
	private static final String TITLE_SUFFIX = "�ڍ�";
	/** �T�u�^�C�g���ڔ��� */
	private static final String SUB_TITLE_SUFFIX = "��{���";
	
	/** �^�C�g�� �ڋq�敪 1:�Ǘ����(��Ɗ܂�) */
	private static final String TITLE_REAL_ESTATE = "�Ǘ����";
	/** �^�C�g�� �ڋq�敪 2:��Ɓi���̂݁j */
	private static final String TITLE_LANDLORD = "���";
	/** �^�C�g�� �ڋq�敪 3:���� */
	private static final String TITLE_PROPERTY = "����";
	/** �^�C�g�� �ڋq�敪 4:�����ҁE�l */
	private static final String TITLE_TENANT = "�����ҁE�l";
	
	/** ��ʋ敪 */
	private String gamenKbn;
	/** �ڋqID */
	private String kokyakuId;
	/** �ڍ׉�ʋ敪 */
	private String detailKbn;
	/** ��ʕ\���敪�i�J�ڌ���ʋ敪�j */
	private String dispKbn;

	/** �ڋq�e�[�u��Entity */
	private RcpMKokyaku kokyakuEntity;
	
	/** ���������i�ڋq������ʁj */
	private RC011KokyakuSearchCondition customerCondition = new RC011KokyakuSearchCondition();
	
	/** �J�ڌ���ʋ敪 */
	private String fromDispKbn;
	
	/**
	 * ��ʋ敪���擾���܂��B
	 *
	 * @return ��ʋ敪
	 */
	public String getGamenKbn() {
		return gamenKbn;
	}
	/**
	 * ��ʋ敪��ݒ肵�܂��B
	 *
	 * @param actionType ��ʋ敪
	 */
	public void setGamenKbn(String gamenKbn) {
		this.gamenKbn = gamenKbn;
	}

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
	 * �J�ڐ��ʋ敪���擾���܂��B
	 *
	 * @return �J�ڐ��ʋ敪
	 */
	public String getDetailKbn() {
		return detailKbn;
	}
	/**
	 * �J�ڐ��ʋ敪��ݒ肵�܂��B
	 *
	 * @param detailKbn �J�ڐ��ʋ敪
	 */
	public void setDetailKbn(String detailKbn) {
		this.detailKbn = detailKbn;
	}
	
	/**
	 * ��ʕ\���敪�i�J�ڌ���ʋ敪�j���擾���܂��B
	 *
	 * @return ��ʕ\���敪�i�J�ڌ���ʋ敪�j
	 */
	public String getDispKbn() {
		return dispKbn;
	}
	/**
	 * ��ʕ\���敪�i�J�ڌ���ʋ敪�j��ݒ肵�܂��B
	 *
	 * @param dispKbn ��ʕ\���敪�i�J�ڌ���ʋ敪�j
	 */
	public void setDispKbn(String dispKbn) {
		this.dispKbn = dispKbn;
	}
	
	/**
	 * �ڋq�e�[�u��Entity���擾���܂��B
	 * 
	 * @return �ڋq�e�[�u��Entity
	 */
	public RcpMKokyaku getKokyakuEntity() {
		return kokyakuEntity;
	}
	
	/**
	 * �ڋq�e�[�u��Entity��ݒ肵�܂��B
	 * 
	 * @param kokyakuEntity �ڋq�e�[�u��Entity
	 */
	public void setKokyakuEntity(RcpMKokyaku kokyakuEntity) {
		this.kokyakuEntity = kokyakuEntity;
	}
	
	/**
	 * �\�������ʂ��₢���킹�o�^���𔻒肵�܂��B
	 *
	 * @return true:�₢���킹�o�^�Afalse:����ȊO
	 */
	public boolean isInquiryEntryDisplay() {
		return Constants.GAMEN_KBN_INQUIRY_ENTRY.equals(gamenKbn);
	}
	
	/**
	 * �\�������ʂ������E�����ҏڍׂ��𔻒肵�܂��B
	 *
	 * @return true:�����E�����ҏڍׁAfalse:����ȊO
	 */
	public boolean isCustomerDetailDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL.equals(gamenKbn);
	}
	
	/**
	 * �\�������ʂ��t����񂩂𔻒肵�܂��B
	 *
	 * @return true:�t�����Afalse:����ȊO
	 */
	public boolean isAccompanyingDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_ACCOMPANYING.equals(gamenKbn);
	}

	/**
	 * �\�������ʂ��_���񂩂𔻒肵�܂��B
	 *
	 * @return true:�_����Afalse:����ȊO
	 */
	public boolean isContractDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT.equals(gamenKbn);
	}

	/**
	 * �\�������ʂ��_����ڍ׎Q�Ƃ��𔻒肵�܂��B
	 *
	 * @return true:�_����Afalse:����ȊO
	 */
	public boolean isContractDetailDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_CONTRACT_DETAIL.equals(gamenKbn);
	}

	/**
	 * �\�������ʂ��₢���킹�ڍח�����񂩂𔻒肵�܂��B
	 *
	 * @return true:�₢���킹���Afalse:����ȊO
	 */
	public boolean isInquiryHistoryDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_INQUIRY_HISTORY.equals(gamenKbn);
	}

	/**
	 * �\�������ʂ��˗�������񂩂𔻒肵�܂��B
	 *
	 * @return true:�˗��������Afalse:����ȊO
	 */
	public boolean isRequestHistoryDisplay() {
		return Constants.GAMEN_KBN_CUSTOMER_DETAIL_REQUEST_HISTORY.equals(gamenKbn);
	}
	
	/**
	 * �\�������ʂ��˗����e�ڍׁE��Ə󋵓o�^���𔻒肵�܂��B
	 * 
	 * @return true�F�˗����e�ڍׁE��Ə󋵓o�^
	 */
	public boolean isRequestDetailDisplay() {
		return Constants.GAMEN_KBN_REQUEST_DETAIL.equals(gamenKbn);
	}
	
	/**
	 * �\�������ʂ��₢���킹���e�ڍׂ��𔻒肵�܂��B
	 * 
	 * @return true�F�₢���킹���e�ڍ�
	 */
	public boolean isInquiryDetailDisplay() {
		return Constants.GAMEN_KBN_INQUIRY_DETAIL.equals(gamenKbn);
	}
	
	/**
	 * �ڋq�敪�𔻒肵�A�^�C�g����ԋp���܂��B
	 * 
	 * @return �^�C�g��������
	 */
	public String getTitle() {
		
		// �ڋq��񂪎擾�ł��Ȃ��ꍇ
		if (this.kokyakuEntity == null) {
			return TITLE_SUFFIX;	// �^�C�g���ڔ����̂�
		}
		
		String title = "";
		
		if (isRealEstate()) {
			title = TITLE_REAL_ESTATE;
		} else if (isLandlord()) {
			title = TITLE_LANDLORD;
		} else if (isProperty()) {
			title = TITLE_PROPERTY;
		} else if (isTenant()) {
			title = TITLE_TENANT;
		}
		
		// �^�C�g���ڔ�����t�^���ĕԋp
		return title + TITLE_SUFFIX;
	}
	
	/**
	 * �ڋq�敪�𔻒肵�A�T�u�^�C�g����ԋp���܂��B
	 * 
	 * @return �^�C�g��������
	 */
	public String getSubTitle() {
		// �ڋq��񂪎擾�ł��Ȃ��ꍇ
		if (this.kokyakuEntity == null) {
			return SUB_TITLE_SUFFIX;	// �^�C�g���ڔ����̂�
		}
		
		String subTitle = "";
		
		if (isRealEstate()) {
			subTitle = TITLE_REAL_ESTATE;
		} else if (isLandlord()) {
			subTitle = TITLE_LANDLORD;
		} else if (isProperty()) {
			subTitle = TITLE_PROPERTY;
		} else if (isTenant()) {
			subTitle = TITLE_TENANT;
		}
		
		// �T�u�^�C�g���ڔ�����t�^���ĕԋp
		return subTitle + SUB_TITLE_SUFFIX;
	}
	
	/**
	 * �ڋq��������\�����邩���肵�܂��B
	 * 
	 * @return true : �\���Afalse : ��\��
	 */
	public boolean isSearchInputDisplay() {
		
		return isAccompanyingDisplay()
			|| isContractDisplay()
			|| isContractDetailDisplay()
			|| isInquiryHistoryDisplay()
			|| isRequestHistoryDisplay();
	}
	
	/**
	 * �_����Q�ƃ����N��\�����邩���肵�܂��B
	 * 
	 * @return true : �\���Afalse : ��\��
	 */
	public boolean isContractReferenceDisplay() {
		
		if (this.kokyakuEntity == null) {
			// �ڋq��񂪎擾�ł��Ȃ��ꍇ�́A��\��
			return false;
		}
		
		 return ! (isCustomerDetailDisplay()
				|| isAccompanyingDisplay()
				|| isContractDisplay()
				|| isContractDetailDisplay()
				|| isInquiryHistoryDisplay()
				|| isRequestHistoryDisplay());
	}

	/**
	 * �ڋq�敪���s���Y�E�Ǘ���Ђɑ����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�s���Y�E�Ǘ���ЁAfalse:����ȊO
	 */
	public boolean isRealEstate() {
		
		if (this.kokyakuEntity == null) {
			return false;
		}

		return this.kokyakuEntity.isKokyakuKbnFudosan();
	}

	/**
	 * �ڋq�敪�������ɑ����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�����Afalse:����ȊO
	 */
	public boolean isProperty() {
		
		if (this.kokyakuEntity == null) {
			return false;
		}

		return this.kokyakuEntity.isKokyakuKbnBukken();
	}

	/**
	 * �ڋq�敪����Ƃɑ����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:��ƁAfalse:����ȊO
	 */
	public boolean isLandlord() {
		
		if (this.kokyakuEntity == null) {
			return false;
		}

		return this.kokyakuEntity.isKokyakuKbnOoya();
	}

	/**
	 * �ڋq�敪�������ҁE�l�ɑ����Ă��邩�𔻒肵�܂��B
	 *
	 * @return true:�����ҁE�l�Afalse:����ȊO
	 */
	public boolean isTenant() {
		
		if (this.kokyakuEntity == null) {
			return false;
		}

		return this.kokyakuEntity.isKokyakuKbnNyukyosha();
	}
	
	/**
	 * �ڋqID�����݂��邩���肵�܂��B
	 * 
	 * @return true:���݂���
	 */
	public boolean isKokyakuIdExists() {
		return StringUtils.isNotBlank(this.kokyakuId);
	}
	
	/**
	 * ���������i�ڋq������ʁj���擾���܂��B
	 *
	 * @return ��������
	 */
	public RC011KokyakuSearchCondition getCustomerCondition() {
		return customerCondition;
	}

	/**
	 * ���������i�ڋq������ʁj��ݒ肵�܂��B
	 *
	 * @param customerCondition ���������i�ڋq������ʁj
	 */
	public void setCustomerCondition(RC011KokyakuSearchCondition customerCondition) {
		this.customerCondition = customerCondition;
	}
	
	/**
	 * �J�ڌ���ʋ敪���擾���܂��B
	 *
	 * @return �J�ڌ���ʋ敪
	 */
	public String getFromDispKbn() {
		return fromDispKbn;
	}
	
	/**
	 * �J�ڌ���ʋ敪��ݒ肵�܂��B
	 *
	 * @param fromDispKbn �J�ڌ���ʋ敪
	 */
	public void setFromDispKbn(String fromDispKbn) {
		this.fromDispKbn = fromDispKbn;
	}
	
	/**
	 * �u�߂�v�{�^����\�����邩����
	 * 
	 * @return true�i�J�ڌ���ʋ敪���utb041�v�j�F�u�߂�v�{�^���\��
	 */
	public boolean isBackButtonView(){
		return Constants.GAMEN_KBN_CUSTOMER_SEARCH.equals(this.fromDispKbn);
	}
	
	/**
	 * �J�ڌ���ʂ��₢���킹������ʂ��𔻒肵�܂��B
	 * 
	 * @return true�F�₢���킹�������
	 */
	public boolean isFromInquirySearch() {
		return Constants.GAMEN_KBN_INQUIRY_SEARCH.equals(this.dispKbn);
	}
}