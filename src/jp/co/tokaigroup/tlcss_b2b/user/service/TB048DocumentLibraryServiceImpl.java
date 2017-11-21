package jp.co.tokaigroup.tlcss_b2b.user.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tokaigroup.reception.context.TLCSSB2BUserContext;
import jp.co.tokaigroup.reception.dao.TbTPublishFileDao;
import jp.co.tokaigroup.reception.entity.RcpMKokyaku;
import jp.co.tokaigroup.reception.entity.RcpMSystem;
import jp.co.tokaigroup.reception.entity.TbTPublishFile;
import jp.co.tokaigroup.si.fw.exception.ApplicationException;
import jp.co.tokaigroup.si.fw.exception.ValidationException;
import jp.co.tokaigroup.si.fw.message.ValidationPack;
import jp.co.tokaigroup.tlcss_b2b.common.service.TLCSSB2BBaseService;
import jp.co.tokaigroup.tlcss_b2b.user.logic.TB040CustomerCommonInfoLogic;
import jp.co.tokaigroup.tlcss_b2b.user.model.TB048DocumentLibraryModel;

/**
 * 文書ライブラリ一覧サービス実装クラス。
 *
 * @author C.Kobayashi
 * @version 1.0 2015/10/28
 */
@Service
@Transactional(value="txManager", readOnly = true)
public class TB048DocumentLibraryServiceImpl extends TLCSSB2BBaseService
	implements TB048DocumentLibraryService {

	/** 顧客基本情報取得ロジック */
	@Autowired
	private TB040CustomerCommonInfoLogic kokyakuKihon;

	/** TORES文書ライブラリテーブルDAO */
	@Autowired
	private TbTPublishFileDao publishFileDao;

	/**
	 * 初期表示処理を行います。
	 *
	 * @return TORES文書ライブラリ一覧画面モデル
	 * @throws Exception 実行時例外が発生した場合
	 */
	public TB048DocumentLibraryModel getInitInfo(TB048DocumentLibraryModel model) {
		// 初期表示パラメータチェック
		if (StringUtils.isBlank(model.getKokyakuId())) {
			// パラメータの顧客ＩＤが取得できない場合は、パラメータエラー
			throw new ApplicationException("パラメータの顧客ＩＤが空欄です。");
		}

		// セッションから添付可能最大件数を取得
		TLCSSB2BUserContext userContext = (TLCSSB2BUserContext) getUserContext();
		int maxAppendableCount = userContext.getSystgemContstantAsInt(RcpMSystem.RCP_M_SYSTEM_PUBLISH_FILE_TO_MAX);

		// 顧客基本情報取得
		RcpMKokyaku kokyaku = kokyakuKihon.getKokyakuInfo(model.getKokyakuId());
		if (kokyaku == null) {
			// 顧客基本情報が取得できない場合は、エラー
			throw new ValidationException(new ValidationPack().addError("MSG0003"));
		}

		// 文書ライブラリ一覧取得
		List<TbTPublishFile> publishFileList = publishFileDao.selectBy(model.getKokyakuId(), null);
		
		// 文書ライブラリ一覧表示時にリストの数を使うため、値を退避
		model.setListSize(publishFileList.size());
		
		if (publishFileList != null) {
			if (publishFileList.size() > maxAppendableCount) {
				// 添付可能最大件数よりリストの数が多い場合
				// 添付可能最大件数分のみ、リストを表示
				publishFileList = publishFileList.subList(0, maxAppendableCount);
			} else if (publishFileList.size() < maxAppendableCount) {
				// 添付可能最大件数に達していない場合は、空の情報を作成
				for (int i = publishFileList.size(); i < maxAppendableCount; i++) {
					publishFileList.add(new TbTPublishFile());
				}
			}
		}

		model.setKokyaku(kokyaku);
		model.setPublishFileList(publishFileList);

		return model;
	}
}
