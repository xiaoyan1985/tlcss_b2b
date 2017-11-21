package jp.co.tokaigroup.tlcss_b2b.common.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;

@Service
@Transactional(value="txManager", readOnly = true)
@Scope("prototype")
public class TB024InquiryHistoryEntryTestServiceImpl extends TLCSSB2BBaseActionSupport
implements TB024InquiryHistoryEntryTestService{

}
