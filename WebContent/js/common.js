$(document).ready(function() {
	$.ajaxSetup({ cache: false });

	// �X�}�[�g�t�H������
	if ((navigator.userAgent.indexOf('iPhone') > 0 && navigator.userAgent.indexOf('iPad') == -1)
		|| navigator.userAgent.indexOf('iPod') > 0
		|| navigator.userAgent.indexOf('Android') > 0) {

		isSmartPhone = true;

	}else {
		isSmartPhone = false;
	}

	// iPad����
	if ((navigator.userAgent.indexOf('iPad') != -1)) {
		isIPad = true;
	} else {
		isIPad = false;
	}

	// �v���[�X�z���_�[�L������
	if ('placeholder' in document.createElement('input')) {
		isValidPlaceHolder = true;
	} else {
		isValidPlaceHolder = false;
	}

	// �v���[�X�z���_�̏�����
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
					// DatePicker�Ƃ̑����������ׁA�R�����g��
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

			// �v���[�X�z���_�[�ێ��̃e�L�X�g�̂ݏ���
			$(this).css('color', $(document.body).css('color'));
		});

		$(":text").blur(function(e) {
			if ($(this).attr('placeholder') == null) {
				return false;
			}

			if (($(this).val() == '' || $(this).val() == $(this).data('placeholder-string')) && isValidPlaceHolder == false) {
				// �v���[�X�z���_�[�ێ��̃e�L�X�g�̂ݏ���
				$(this).css('color', 'silver');
			}
		});

	} else {
		// dateYMD�N���X�ɑ΂��ăJ�����_�[�@�\��ǉ�����B
		$('.dateYMD').each(function(i,e) {
			$(e)
				// firstDay�F�T�̍ŏ��̗j����ݒ�iTOKAI�J�����_�[�͌��j�n�܂�̂���1���w��j
				// changeMonth�Ftrue - ���̕ύX���v���_�E���ɂ���
				// changeYear�Ftrue - �N�̕ύX���v���_�E���ɂ���
				// showButtonPanel�F�����{�^���A����{�^��
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
					// DatePicker�Ƃ̑����������ׁA�R�����g��
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

		// Enter�L�[��������submit�����̂��u���b�N
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

			// �v���[�X�z���_�[�ێ��̃e�L�X�g�̂ݏ���
			$(this).css('color', $(document.body).css('color'));
		});

		$(":text").blur(function(e) {
			if ($(this).attr('placeholder') == null) {
				return false;
			}

			if (($(this).val() == '' || $(this).val() == $(this).data('placeholder-string')) && isValidPlaceHolder == false) {
				// �v���[�X�z���_�[�ێ��̃e�L�X�g�̂ݏ���
				$(this).css('color', 'silver');
			}
		});
	}

	$("#btnClose").click(function() {
		window.close();
	});

	// �A�N�V�������̐ݒ�
	var action = $('#main').attr('action');

	if (action) {
		var contextPath = $('#main').attr('action').replace(/[^\/]+$/, '');

		$('#main').data({
			"selectRealEstateAgencyAction" : contextPath + "15_realestate_agency_select.html",
			"selectContractorAction"       : contextPath + "16_contractor_select.html"});
	}

	if (!$("#noFocus")[0] || !$("noFocus").val("1")) {
		// hidden�ɂāA�t�H�[�J�X�������w�肳��Ă��Ȃ��ꍇ�ɂ́A�t�H�[�J�X����
		window.focus();
	}
});


//**********************************************************
// �e��ʂ̃R���g���[���Ɏq��ʂŎ擾�����l����
//**********************************************************
function setValueToParent(parentName, index, value) {
	// �e��ʂ̑��݃`�F�b�N
	if(window.opener && ! window.opener.closed){
		opener.$("input[name='" + parentName + "']").val(value).blur();
	}
	window.close();
}

//*******************************************************
// ���t�t�H�[�}�b�g�֐�
//
// YYYY/M/D �� YYYY/MM/DD �̌`���ɕϊ����܂��B
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
//�V�X�e�����t�擾�֐�
//
//�V�X�e�����t��YYYY/MM/DD �̌`���Ŏ擾���܂��B
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
// ajax��JSON�擾Request�𑗐M����
//
// ����
//   url             : URL
//   datas           : �p�����[�^
//   successFunction : �ʐM�������R�[���o�b�N�֐�
//*******************************************************
function sendJSONRequest(url, datas, successFunction) {

	// �p�����[�^����
	var data = '';
	for (key in datas) {
		if (data != '') {
			data = data + '&';
		}
		data = data + key + '=' + encodeURIComponent(datas[key]);
	}

	// JSONRequest���M
	$.ajax({
		type : 'POST',
		cache: false,
		data : data,
		url : url,
		beforeSend : function() {
			$.prettyLoader.show();

			// select��z-index�o�O���
			$('select').hide();

			// overlay�\��
			var height = document.body.scrollHeight;
			var div = '<div id="overlay" style="width:100%;height:' + height + ';z-index:9999;position:absolute;top:0px;left:0px;background-color:#ffffff;filter:alpha(opacity:50)"></div>';
			$('body').append(div);
		},
		success : function(data, dataType) {
			// null���󕶎��ɕϊ�
			for (key in data) {
				if (data[key] == null) {
					data[key] = "";
				}
			}

			successFunction(data, dataType);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

			if (textStatus == 'timeout') {
				alert('�ڑ����^�C���A�E�g���܂����B');
			} else {
				// �V�X�e���G���[��ʂ֑J��
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
//���X�g���č쐬����B
//
//����
// selectId  : ���X�gID
// list      : ���X�g�f�[�^
// valCd     : ���X�g�f�[�^���Avalue���ۑ�����Ă���L�[
// textCd    : ���X�g�f�[�^���Atext���ۑ�����Ă���L�[
// hasEmpty  : ���X�g�擪�ɋ󗓂�݂��邩�ǂ���
//*******************************************************
function remakeList(selectId, list, valCd, textCd, hasEmpty) {
	if (list != null) {
		// ���I��l�擾
		var selected = $('#'+selectId).val();
		// �q�v�f�폜
		$('#'+selectId+'> option').remove();

		// ��v�f�ǉ�
		if (hasEmpty) {
			$('#'+selectId).append($('<option>').html('').val(''));
		}

		// �q�v�f�ǉ�
		for (i=0; i<list.length; i++){
			var obj = list[i];

			$('#'+selectId).append($('<option>').html(obj[textCd]).val(obj[valCd]));
		}
		// �V�I��l�ݒ�
		if (selected != null) {
			// setTimeout�ɂ�IE6�̃o�O�����
			// �Q�l�����@http://d.hatena.ne.jp/kanonji/20120705/1341463719
			setTimeout( function() {
				$('#'+selectId).val(selected);
			}, 1);
		}
	}
};

//*******************************************************
// �������̃��b�Z�[�W���N���A����B
//*******************************************************
function clearSuccessMsg(msgs) {
	$('#successMsgArea').html('');
}
//*******************************************************
// �������̃��b�Z�[�W��\������B
//*******************************************************
function showSuccessMsg(msgs) {
	// ���b�Z�[�W�̈� �N���A
	clearSuccessMsg();

	// ���b�Z�[�W�ǋL
	for (i=0; i<msgs.length; i++) {
		$('#successMsgArea').append('<span class="successMsg">'+msgs[i]+'</span>');
	}
}

//*******************************************************
// �G���[���̃��b�Z�[�W���N���A����B
//*******************************************************
function clearErrorMsg() {
	$('#errorMsgArea').html('');
}
//*******************************************************
// �G���[���̃��b�Z�[�W��\������B
//*******************************************************
function showErrorMsg(msgs) {
	// �G���[���b�Z�[�W�̈� �N���A
	clearErrorMsg();

	// �G���[���b�Z�[�W�ǋL
	for (i=0; i<msgs.length; i++) {
		$('#errorMsgArea').append('<span class="errorMsg">'+msgs[i]+'</span>');
	}
}

//*******************************************************
// �q��ʍ쐬����
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
// �q��ʍ쐬�����i�E�B���h�E���̂ݎw��j
//*******************************************************
function createWindow1(winName) {
	var newWin = window.open("", winName,"toolbar=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,left=0,top=0");

	try{
		newWin.resizeTo(width=1024,height=screen.availHeight);
	}catch(e){
		//�u�A�N�Z�X�����ۂ���܂����v�ȊO�̃G���[�̏ꍇ
		if(e.number != -2147024891){
			//�G���[���e��Ԃ�
			throw e;
		}
	}
	return newWin;
}

//*******************************************************
// �q��ʍ쐬�����i�E�B���h�E���A���w��j
//*******************************************************
function createWindow2(winName, x) {
	var newWin = window.open("", winName,"toolbar=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,left=0,top=0");

	try{
		newWin.resizeTo(width=x,height=screen.availHeight);
	}catch(e){
		//�u�A�N�Z�X�����ۂ���܂����v�ȊO�̃G���[�̏ꍇ
		if(e.number != -2147024891){
			//�G���[���e��Ԃ�
			throw e;
		}
	}
	return newWin;
}

//*******************************************************
// �q��ʍ쐬�����i�E�B���h�E���A���A�����w��j
//*******************************************************
function createWindow3(winName, x, y) {
	var newWin = window.open("", winName,"toolbar=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,left=0,top=0");

	try{
		newWin.resizeTo(width=x,height=y);
	}catch(e){
		//�u�A�N�Z�X�����ۂ���܂����v�ȊO�̃G���[�̏ꍇ
		if(e.number != -2147024891){
			//�G���[���e��Ԃ�
			throw e;
		}
	}
	return newWin;
}

//*******************************************************
//�q��ʍ쐬�����i�E�B���h�E���A���A�����A�c�ʒu�w��j
//*******************************************************
function createWindow4(winName, x, y, top) {
	var newWin = window.open("", winName,"toolbar=no,resizable=yes,menubar=no,scrollbars=yes,status=yes,left=0");

	try{
		newWin.resizeTo(width=x,height=y);
		newWin.moveTo(0, top);
	}catch(e){
		//�u�A�N�Z�X�����ۂ���܂����v�ȊO�̃G���[�̏ꍇ
		if(e.number != -2147024891){
			//�G���[���e��Ԃ�
			throw e;
		}
	}
	return newWin;
}


//**********************************************************
// �s���Y�E�Ǘ���БI��
//
// �ďo���`��
//   selectRealEstateAgency(target)
//
// ����:
//   target       : �s���Y�E�Ǘ���ЃR�[�h�̓������v�f��id
//**********************************************************
function selectRealEstateAgency(target) {
	// �e��ʓ��͂�name
	$('<input type="hidden" name="customerCdResultNm" class="temporaryFormForSelect" />')
		.val($('#' + target))
		.appendTo('#main');

	// ��ʂ̐���
	var w = createWindow("realestate_select_win", 1000, 700);
	var form = document.forms[0];

	// �������s
	form.action = $('#main').data('selectRealEstateAgencyAction');
	form.target = w.name;
	form.submit();

	// �p�����[�^���M�̂��߂̃t�H�[���폜
	$('#main .temporaryFormForSelect').remove();
}

//**********************************************************
// �ƎґI��
//
// �ďo���`��
//   selectContractor(target)
//
// ����:
//   target       : �Ǝ҃R�[�h�̓������v�f��id
//**********************************************************
function selectContractor(target) {
	// �e��ʓ��͂�name
	$('<input type="hidden" name="customerCdResultNm" class="temporaryFormForSelect" />')
		.val($('#' + target))
		.appendTo('#main');

	// ��ʂ̐���
	var w = createWindow("contractor_select_win", 1000, 700);
	var form = document.forms[0];

	// �������s
	form.action = $('#main').data('selectContractorAction');
	form.target = w.name;
	form.submit();

	// �p�����[�^���M�̂��߂̃t�H�[���폜
	$('#main .temporaryFormForSelect').remove();
}