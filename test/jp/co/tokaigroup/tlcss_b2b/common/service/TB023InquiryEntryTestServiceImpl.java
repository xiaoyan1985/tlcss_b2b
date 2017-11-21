package jp.co.tokaigroup.tlcss_b2b.common.service;

import jp.co.tokaigroup.tlcss_b2b.common.action.TLCSSB2BBaseActionSupport;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value="txManager", readOnly = true)
@Scope("prototype")
public class TB023InquiryEntryTestServiceImpl extends TLCSSB2BBaseActionSupport
		implements TB023InquiryEntryTestService {
}
