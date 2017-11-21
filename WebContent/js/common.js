$(document).ready(function() {
	$.ajaxSetup({ cache: false });

	// スマートフォン判定
	if ((navigator.userAgent.indexOf('iPhone') > 0 && navigator.userAgent.indexOf('iPad') == -1)
		|| navigator.userAgent.indexOf('iPod') > 0
		|| navigator.userAgent.indexOf('Android') > 0) {

		isSmartPhone = true;

	}else {
		isSmartPhone = false;
	}

	// iPad判定
	if ((navigator.userAgent.indexOf('iPad') != -1)) {
		isIPad = true;
	} else {
		isIPad = false;
	}

	// プレースホルダー有効判定
	if ('placeholder' in document.createElement('input')) {
		isValidPlaceHolder = true;
	} else {
		isValidPlaceHolder = false;
	}

	// プレースホルダの初期化
	$('[placeholder]').ahPlaceholder({
		placeholderColor : 'silver',
		placeholderAttr : 'placeholder',
		likeApple : false
	});

	if (isSmartPhone) {
		$('.dateYMD').each(function(i,e) {
			$(e)
				.blur(function(){
					$(this).val(ConvSlash(ConvZeroYMD(ZenToHan_Num($(this).val())), 1));
				})
				.focus(function(){
					$(this).val(ConvDelSlash($(this).val()));
					// DatePickerとの相性が悪い為、コメント化
					// $(this).select();
				});
		});

		$('.time').each(function(i,e) {
			$(e)
				.blur(function(){
					$(this).val(ConvCoron(ZenToHan_Num($(this).val())));
				})
				.focus(function(){
					$(this).val(ConvDelCoron($(this).val()));
				});
		});

		$(":text").focus(function(e) {
			if ($(this).attr('placeholder') == null) {
				return false;
			}

			// プレースホルダー保持のテキストのみ処理
			$(this).css('color', $(document.body).css('color'));
		});

		$(":text").blur(function(e) {
			if ($(this).attr('placeholder') == null) {
				return false;
			}

			if (($(this).val() == '' || $(this).val() == $(this).data('placeholder-string')) && isValidPlaceHolder == false) {
				// プレースホルダー保持のテキストのみ処理
				$(this).css('color', 'silver');
			}
		});

	} else {
		// dateYMDクラスに対してカレンダー機能を追加する。
		$('.dateYMD').each(function(i,e) {
			$(e)
				// firstDay：週の最初の曜日を設定（TOKAIカレンダーは月曜始まりのため1を指定）
				// changeMonth：true - 月の変更をプルダウンにする
				// changeYear：true - 年の変更をプルダウンにする
				// showButtonPanel：今日ボタン、閉じるボタン
				.datepicker({
					firstDay: 1,
	//						showOtherMonths: true,
	//						selectOtherMonths: true,
					changeMonth: true,
					changeYear: true,
					showOn: "button",
					showButtonPanel: true,
					buttonImage: "images/calendar.gif",
					buttonImageOnly: true,
					duration:"fast",
					showAnim:"",
					minDate: '-5y',
					maxDate: '+5y',
					beforeShowDay: function(date) {
						var result;
						switch (date.getDay()) {
							case 0:
								result = [true, "date-holiday"];
								break;
							case 6:
								result = [true, "date-saturday"];
								break;
							default:
								result = [true];
								break;
						}
						return result;
					},
					onSelect: function(dateText, inst) {
						$(this).css('color', '#7A6A6C');
					}
				})
				.blur(function(){
					$(this).val(ConvSlash(ConvZeroYMD(ZenToHan_Num($(this).val())), 1));
				})
				.focus(function(){
					$(this).val(ConvDelSlash($(this).val()));
					// DatePickerとの相性が悪い為、コメント化
					// $(this).select();
				});
		});

		$('.time').each(function(i,e) {
			$(e)
				.blur(function(){
					$(this).val(ConvCoron(ZenToHan_Num($(this).val())));
				})
				.focus(function(){
					$(this).val(ConvDelCoron($(this).val()));
				});
		});

		// Enterキー押下時にsubmitされるのをブロック
		$(":text").keypress(function(ev) {
			if ((ev.which && ev.which === 13) || (ev.keyCode && ev.keyCode === 13)) {
				return false;
			} else {
				return true;
			}
		});

		$(":text").focus(function(e) {
			if ($(this).attr('placeholder') == null) {
				return false;
			}

			// プレースホルダー保持のテキストのみ処理
			$(this).css('color', $(document.body).css('color'));
		});

		$(":text").blur(function(e) {
			if ($(this).attr('placeholder') == null) {
				return false;
			}

			if (($(this).val() == '' || $(this).val() == $(this).data('placeholder-string')) && isValidPlaceHolder == false) {
				// プレースホルダー保持のテキストのみ処理
				$(this).css('color', 'silver');
			}
		});
	}

	$("#btnClose").click(function() {
		window.close();
	});

	// アクション名の設定
	var action = $('#main').attr('action');

	if (action) {
		var contextPath = $('#main').attr('action').replace(/[^\/]+$/, '');

		$('#main').data({
			"selectRealEstateAgencyAction" : contextPath + "15_realestate_agency_select.html",
			"selectContractorAction"       : contextPath + "16_contractor_select.html"});
	}

	if (!$("#noFocus")[0] || !$("noFocus").val("1")) {
		// hiddenにて、フォーカス無しが指定されていない場合には、フォーカスする
		window.focus();
	}
});


//**********************************************************
// 親画面のコントロールに子画面で取得した値を代入
//**********************************************************
function setValueToParent(parentName, index, value) {
	// 親画面の存在チェック
	if(window.opener && ! window.opener.closed){
		opener.$("input[name='" + parentName + "']").val(value).blur();
	}
	window.close();
}

//*******************************************************
// 日付フォーマット関数
//
// YYYY/M/D → YYYY/MM/DD の形式に変換します。
//*******************************************************
function dateFormat(date) {
	var dateYear = date.split("/")[0];
	var dateMonth = date.split("/")[1];
	var dateDay = date.split("/")[2];

	if (dateMonth && dateDay) {

		if (dateMonth.length == 1) {
			dateMonth = "0" + dateMonth;
		}
		if (dateDay.length == 1) {
			dateDay = "0" + dateDay;
		}
		date = dateYear + "/" + dateMonth + "/" + dateDay;
	}
	return date;
}

//*******************************************************
//システム日付取得関数
//
//システム日付をYYYY/MM/DD の形式で取得します。
//*******************************************************
function getSysDate(){
	var date = new Date();
	var year = date.getYear();
	var year4 = (year < 2000) ? year+1900 : year;
	var month = date.getMonth() + 1;
	var date = date.getDate();

	if (month < 10) {
		month = "0" + month;
	}

	if (date < 10) {
		date = "0" + date;
	}

	var strDate = year4 + "/" + month + "/" + date;
	return strDate;
}


//*******************************************************
// ajaxでJSON取得Requestを送信する
//
// 引数
//   url             : URL
//   datas           : パラメータ
//   successFunction : 通信成功時コールバック関数
//*******************************************************
function sendJSONRequest(url, datas, successFunction) {

	// パラメータ生成
	var data = '';
	for (key in datas) {
		if (data != '') {
			data = data + '&';
		}
		data = data + key + '=' + encodeURIComponent(datas[key]);
	}

	// JSONRequest送信
	$.ajax({
		type : 'POST',
		cache: false,
		data : data,
		url : url,
		beforeSend : function() {
			$.prettyLoader.show();

			// selectのz-indexバグ回避
			$('select').hide();

			// overlay表示
			var height = document.body.scrollHeight;
			var div = '<div id="overlay" style="width:100%;height:' + height + ';z-index:9999;position:absolute;top:0px;left:0px;background-color:#ffffff;filter:alpha(opacity:50)"></div>';
			$('body').append(div);
		},
		success : function(data, dataType) {
			// nullを空文字に変換
			for (key in data) {
				if (data[key] == null) {
					data[key] = "";
				}
			}

			successFunction(data, dataType);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

			if (textStatus == 'timeout') {
				alert('接続がタイムアウトしました。');
			} else {
				// システムエラー画面へ遷移
				location.href = "./error.action";
			}
		},
		complete : function() {
				$.prettyLoader.hide();

				$('#overlay').remove();
				$('select').show();
		}
	});
}

//*******************************************************
//リストを再作成する。
//
//引数
// selectId  : リストID
// list      : リストデータ
// valCd     : リストデータ中、valueが保存されているキー
// textCd    : リストデータ中、textが保存されているキー
// hasEmpty  : リスト先頭に空欄を設けるかどうか
//*******************************************************
function remakeList(selectId, list, valCd, textCd, hasEmpty) {
	if (list != null) {
		// 現選択値取得
		var selected = $('#'+selectId).val();
		// 子要素削除
		$('#'+selectId+'> option').remove();

		// 空要素追加
		if (hasEmpty) {
			$('#'+selectId).append($('<option>').html('').val(''));
		}

		// 子要素追加
		for (i=0; i<list.length; i++){
			var obj = list[i];

			$('#'+selectId).append($('<option>').html(obj[textCd]).val(obj[valCd]));
		}
		// 新選択値設定
		if (selected != null) {
			// setTimeoutにてIE6のバグを回避
			// 参考資料　http://d.hatena.ne.jp/kanonji/20120705/1341463719
			setTimeout( function() {
				$('#'+selectId).val(selected);
			}, 1);
		}
	}
};

//*******************************************************
// 成功時のメッセージをクリアする。
//*******************************************************
function clearSuccessMsg(msgs) {
	$('#successMsgArea').html('');
}
//*******************************************************
// 成功時のメッセージを表示する。
//*******************************************************
function showSuccessMsg(msgs) {
	// メッセージ領域 クリア
	clearSuccessMsg();

	// メッセージ追記
	for (i=0; i<msgs.length; i++) {
		$('#successMsgArea').append('<span class="successMsg">'+msgs[i]+'</span>');
	}
}

//*******************************************************
// エラー時のメッセージをクリアする。
//*******************************************************
function clearErrorMsg() {
	$('#errorMsgArea').html('');
}
//*******************************************************
// エラー時のメッセージを表示する。
//*******************************************************
function showErrorMsg(msgs) {
	// エラーメッセージ領域 クリア
	clearErrorMsg();

	// エラーメッセージ追記
	for (i=0; i<msgs.length; i++) {
		$('#errorMsgArea').append('<span class="errorMsg">'+msgs[i]+'</span>');
	}
}

//*******************************************************
// 子画面作成処理
//*******************************************************
function createWindow(winName, x, y, top) {
	var newWin;

	if (arguments.length == 1) {
		newWin = createWindow1(winName);
	} else if (arguments.length == 2) {
		newWin = createWindow2(winName, x);
	} else if (arguments.length == 3) {
		newWin = createWindow3(winName, x, y);
	} else if (arguments.length == 4) {
		newWin = createWindow4(winName, x, y, top);
	}

	return newWin;
}

//*******************************************************
// 子画面作成処理（ウィンドウ名のみ指定）
//*******************************************************
function createWindow1(winName) {
	var newWin = window.open("", winName,"toolbar=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,left=0,top=0");

	try{
		newWin.resizeTo(width=1024,height=screen.availHeight);
	}catch(e){
		//「アクセスが拒否されました」以外のエラーの場合
		if(e.number != -2147024891){
			//エラー内容を返す
			throw e;
		}
	}
	return newWin;
}

//*******************************************************
// 子画面作成処理（ウィンドウ名、幅指定）
//*******************************************************
function createWindow2(winName, x) {
	var newWin = window.open("", winName,"toolbar=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,left=0,top=0");

	try{
		newWin.resizeTo(width=x,height=screen.availHeight);
	}catch(e){
		//「アクセスが拒否されました」以外のエラーの場合
		if(e.number != -2147024891){
			//エラー内容を返す
			throw e;
		}
	}
	return newWin;
}

//*******************************************************
// 子画面作成処理（ウィンドウ名、幅、高さ指定）
//*******************************************************
function createWindow3(winName, x, y) {
	var newWin = window.open("", winName,"toolbar=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,left=0,top=0");

	try{
		newWin.resizeTo(width=x,height=y);
	}catch(e){
		//「アクセスが拒否されました」以外のエラーの場合
		if(e.number != -2147024891){
			//エラー内容を返す
			throw e;
		}
	}
	return newWin;
}

//*******************************************************
//子画面作成処理（ウィンドウ名、幅、高さ、縦位置指定）
//*******************************************************
function createWindow4(winName, x, y, top) {
	var newWin = window.open("", winName,"toolbar=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,left=0");

	try{
		newWin.resizeTo(width=x,height=y);
		newWin.moveTo(0, top);
	}catch(e){
		//「アクセスが拒否されました」以外のエラーの場合
		if(e.number != -2147024891){
			//エラー内容を返す
			throw e;
		}
	}
	return newWin;
}


//**********************************************************
// 不動産・管理会社選択
//
// 呼出し形式
//   selectRealEstateAgency(target)
//
// 引数:
//   target       : 不動産・管理会社コードの入った要素のid
//**********************************************************
function selectRealEstateAgency(target) {
	// 親画面入力のname
	$('<input type="hidden" name="customerCdResultNm" class="temporaryFormForSelect" />')
		.val($('#' + target))
		.appendTo('#main');

	// 画面の生成
	var w = createWindow("realestate_select_win", 1000, 700);
	var form = document.forms[0];

	// 検索実行
	form.action = $('#main').data('selectRealEstateAgencyAction');
	form.target = w.name;
	form.submit();

	// パラメータ送信のためのフォーム削除
	$('#main .temporaryFormForSelect').remove();
}

//**********************************************************
// 業者選択
//
// 呼出し形式
//   selectContractor(target)
//
// 引数:
//   target       : 業者コードの入った要素のid
//**********************************************************
function selectContractor(target) {
	// 親画面入力のname
	$('<input type="hidden" name="customerCdResultNm" class="temporaryFormForSelect" />')
		.val($('#' + target))
		.appendTo('#main');

	// 画面の生成
	var w = createWindow("contractor_select_win", 1000, 700);
	var form = document.forms[0];

	// 検索実行
	form.action = $('#main').data('selectContractorAction');
	form.target = w.name;
	form.submit();

	// パラメータ送信のためのフォーム削除
	$('#main .temporaryFormForSelect').remove();
}