jQuery.extend(jQuery.validator.methods, {
	min: function(a, b, d) {
		return this.optional(b) || fnc_chk_min(a, d);
	}
});

jQuery.extend(jQuery.validator.messages, {
	required: ART0001,
	minlength: jQuery.format(ART0010),
	email: ART0009,
	dateISO: ART0008,
	number: ART0003,
	digits: ART0003,
	max: jQuery.format(ART0012),
	min: jQuery.format(ART0013),
	maxlength: jQuery.format(ART0022),
	rangelength: jQuery.format(ART0011),
	url: "URL����͂��ĉ������B",
	equalTo: "�����l����͂��ĉ������B",
	range: jQuery.format(" {0} ���� {1} �܂ł̒l����͂��ĉ������B"),
	creditcard: "�N���W�b�g�J�[�h�ԍ�����͂��ĉ������B"
});

//�S�p�̂�
jQuery.validator.addMethod("zenkaku", function(value, element) {
	return this.optional(element) || (ZenChk(value) == 1);
	}, ART0002
);

//���p�̂�
jQuery.validator.addMethod("hankaku", function(value, element) {
	return this.optional(element) || (HanChk(value) == 1);
	}, ART0005
);

//���t�`�F�b�N �f�[�^(yyyymmdd)�̑Ó���
jQuery.validator.addMethod("dateYMD", function(value, element) {
	return this.optional(element) || (DateChk2(value) != -1) || (DateChk(value) != -1);
	}, ART0008
);

//���t�`�F�b�N �f�[�^(yyyymm)�̑Ó���
jQuery.validator.addMethod("dateYM", function(value, element) {
	return this.optional(element) || (DateChk3(value) != -1) || (DateChk4(value) != -1);
	}, ART0008
);

//���p�J�^�J�i�̂�
jQuery.validator.addMethod("hankana", function(value, element) {
	return this.optional(element) || (HanChk(value) == 1);
	}, ART0005
);

//���p�A���t�@�x�b�g�i�啶����������j�������͐����̂�
jQuery.validator.addMethod("alphanum", function(value, element) {
	return this.optional(element) || /^([a-zA-Z0-9]+)$/.test(value);
	}, ART0004
);

//���p���l�ƃn�C�t���̂�
jQuery.validator.addMethod("hyphennum", function(value, element) {
	return this.optional(element) || (NamChk_minus(value) != 0);
	}, ART0007
);

//�S�p�Ђ炪�ȥ�J�^�J�i�̂�
jQuery.validator.addMethod("kana", function(value, element) {
	return this.optional(element) || /^([�@-���[��-��]+)$/.test(value);
	}, "�S�p�Ђ炪�ȥ�J�^�J�i����͂��ĉ������B"
);

//�S�p�Ђ炪�Ȃ̂�
jQuery.validator.addMethod("hiragana", function(value, element) {
	return this.optional(element) || /^([��-��]+)$/.test(value);
	}, "�S�p�Ђ炪�Ȃ���͂��ĉ������B"
);

//�S�p�J�^�J�i�̂�
jQuery.validator.addMethod("katakana", function(value, element) {
	return this.optional(element) || /^([�@-���[]+)$/.test(value);
	}, "�S�p�J�^�J�i����͂��ĉ������B"
);

//���p�A���t�@�x�b�g�i�啶����������j�̂�
jQuery.validator.addMethod("alphabet", function(value, element) {
	return this.optional(element) || /^([a-zA-z\s]+)$/.test(value);
	}, "���p�p������͂��ĉ������B"
);

//�X�֔ԍ��i��:012-3456�j
jQuery.validator.addMethod("postnum", function(value, element) {
	return this.optional(element) || /^\d{3}\-\d{4}$/.test(value);
	}, "�X�֔ԍ�����͂��ĉ������B�i��:123-4567�j"
);

//�g�єԍ��i��:010-2345-6789�j
jQuery.validator.addMethod("mobilenum", function(value, element) {
	return this.optional(element) || /^0\d0-\d{4}-\d{4}$/.test(value);
	}, "�g�єԍ�����͂��ĉ������B�i��:010-2345-6789�j"
);

//�d�b�ԍ��i��:012-345-6789�j
jQuery.validator.addMethod("telnum", function(value, element) {
	return this.optional(element) || /^[0-9-]{12}$/.test(value);
	}, "�d�b�ԍ�����͂��ĉ������B�i��:012-345-6789�j"
);


//�����̌����`�F�b�N
jQuery.validator.addMethod("integer", function(value, element) {
	return this.optional(element) || (fnc_chk_seisu(value, $(element).attr("integer")));
	}, ART0016
);

// �����̌����`�F�b�N
jQuery.validator.addMethod("decimal", function(value, element) {
	return this.optional(element) || (fnc_chk_shosu2(value, $(element).attr("decimal")));
	}, ART0017
);


//���ԃ`�F�b�N �f�[�^(hhmm)�̑Ó���
jQuery.validator.addMethod("time", function(value, element) {
	return this.optional(element) || (TimeChk2(value) != -1) || (TimeChk(value) != -1);
	}, ART0021
);


jQuery.validator.addMethod("byteVarchar", function(value, element, param) {
	return this.optional(element) || (Get_Byte_Kana(delComma(value)) <= param);
	}, ART0022
);

// ������s�����`�F�b�N
jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
	 return this.optional(element) || value != $(param).val();
	}, "This has to be different...");

// ���t�̑召��r
jQuery.validator.addMethod("compareDate", function(value, element) {
	var isNormal = true;

	var fromId = "#" + $(element).attr("dateFrom");
	var toId = "#" + $(element).attr("dateTo");

	var dateFrom = $(fromId).val();
	var dateTo = $(toId).val();
	var placeholder = $.data(element, 'placeholder-string');

	if((dateFrom != placeholder && dateFrom != "")
			&& (dateTo != placeholder && dateTo != "")){

		$(fromId).removeClass("error");
		$(toId).removeClass("error");

		dateFrom = dateFrom.split("/").join("");
		dateTo = dateTo.split("/").join("");

		if(dateFrom > dateTo){
			isNormal = false;
		}
	}
	return isNormal;
	}, ART0026
);

jQuery.validator.addMethod("csv", function(txt) {
	if (txt != "") {
		// �p�X��؂肪\�̃p�X��/�ɒu��
		txt = txt.replace(/\\/g, "/");
		// �t�@�C�����̎擾
		var txtSlash = txt.split("/");
		var length = 0;
		for (var tmp in txtSlash) {
			length++;
		}
		var fileNm = txtSlash[length-1];
		// �g���q�̎擾
		var fileNmDot = fileNm.split(".");
		length = 0;
		for (var tmp in fileNmDot) {
			length++;
		}
		var kakutyoshi = fileNmDot[length-1];
		// �g���q�L������
		if (length == 1 || kakutyoshi == '') {
			// �g���q������
			return false;
		} else {
			// ��ԍŌ�̊g���q��Ώۂ�csv�`�F�b�N
			if (!kakutyoshi.match(/(csv)$/i)) {
				return false;
			}
		}
	}
	return true;
	},ART0027
);



