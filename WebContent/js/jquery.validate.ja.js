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
	url: "URLを入力して下さい。",
	equalTo: "同じ値を入力して下さい。",
	range: jQuery.format(" {0} から {1} までの値を入力して下さい。"),
	creditcard: "クレジットカード番号を入力して下さい。"
});

//全角のみ
jQuery.validator.addMethod("zenkaku", function(value, element) {
	return this.optional(element) || (ZenChk(value) == 1);
	}, ART0002
);

//半角のみ
jQuery.validator.addMethod("hankaku", function(value, element) {
	return this.optional(element) || (HanChk(value) == 1);
	}, ART0005
);

//日付チェック データ(yyyymmdd)の妥当性
jQuery.validator.addMethod("dateYMD", function(value, element) {
	return this.optional(element) || (DateChk2(value) != -1) || (DateChk(value) != -1);
	}, ART0008
);

//日付チェック データ(yyyymm)の妥当性
jQuery.validator.addMethod("dateYM", function(value, element) {
	return this.optional(element) || (DateChk3(value) != -1) || (DateChk4(value) != -1);
	}, ART0008
);

//半角カタカナのみ
jQuery.validator.addMethod("hankana", function(value, element) {
	return this.optional(element) || (HanChk(value) == 1);
	}, ART0005
);

//半角アルファベット（大文字･小文字）もしくは数字のみ
jQuery.validator.addMethod("alphanum", function(value, element) {
	return this.optional(element) || /^([a-zA-Z0-9]+)$/.test(value);
	}, ART0004
);

//半角数値とハイフンのみ
jQuery.validator.addMethod("hyphennum", function(value, element) {
	return this.optional(element) || (NamChk_minus(value) != 0);
	}, ART0007
);

//全角ひらがな･カタカナのみ
jQuery.validator.addMethod("kana", function(value, element) {
	return this.optional(element) || /^([ァ-ヶーぁ-ん]+)$/.test(value);
	}, "全角ひらがな･カタカナを入力して下さい。"
);

//全角ひらがなのみ
jQuery.validator.addMethod("hiragana", function(value, element) {
	return this.optional(element) || /^([ぁ-ん]+)$/.test(value);
	}, "全角ひらがなを入力して下さい。"
);

//全角カタカナのみ
jQuery.validator.addMethod("katakana", function(value, element) {
	return this.optional(element) || /^([ァ-ヶー]+)$/.test(value);
	}, "全角カタカナを入力して下さい。"
);

//半角アルファベット（大文字･小文字）のみ
jQuery.validator.addMethod("alphabet", function(value, element) {
	return this.optional(element) || /^([a-zA-z\s]+)$/.test(value);
	}, "半角英字を入力して下さい。"
);

//郵便番号（例:012-3456）
jQuery.validator.addMethod("postnum", function(value, element) {
	return this.optional(element) || /^\d{3}\-\d{4}$/.test(value);
	}, "郵便番号を入力して下さい。（例:123-4567）"
);

//携帯番号（例:010-2345-6789）
jQuery.validator.addMethod("mobilenum", function(value, element) {
	return this.optional(element) || /^0\d0-\d{4}-\d{4}$/.test(value);
	}, "携帯番号を入力して下さい。（例:010-2345-6789）"
);

//電話番号（例:012-345-6789）
jQuery.validator.addMethod("telnum", function(value, element) {
	return this.optional(element) || /^[0-9-]{12}$/.test(value);
	}, "電話番号を入力して下さい。（例:012-345-6789）"
);


//整数の桁数チェック
jQuery.validator.addMethod("integer", function(value, element) {
	return this.optional(element) || (fnc_chk_seisu(value, $(element).attr("integer")));
	}, ART0016
);

// 小数の桁数チェック
jQuery.validator.addMethod("decimal", function(value, element) {
	return this.optional(element) || (fnc_chk_shosu2(value, $(element).attr("decimal")));
	}, ART0017
);


//時間チェック データ(hhmm)の妥当性
jQuery.validator.addMethod("time", function(value, element) {
	return this.optional(element) || (TimeChk2(value) != -1) || (TimeChk(value) != -1);
	}, ART0021
);


jQuery.validator.addMethod("byteVarchar", function(value, element, param) {
	return this.optional(element) || (Get_Byte_Kana(delComma(value)) <= param);
	}, ART0022
);

// 文字列不等号チェック
jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
	 return this.optional(element) || value != $(param).val();
	}, "This has to be different...");

// 日付の大小比較
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
		// パス区切りが\のパスは/に置換
		txt = txt.replace(/\\/g, "/");
		// ファイル名の取得
		var txtSlash = txt.split("/");
		var length = 0;
		for (var tmp in txtSlash) {
			length++;
		}
		var fileNm = txtSlash[length-1];
		// 拡張子の取得
		var fileNmDot = fileNm.split(".");
		length = 0;
		for (var tmp in fileNmDot) {
			length++;
		}
		var kakutyoshi = fileNmDot[length-1];
		// 拡張子有無判定
		if (length == 1 || kakutyoshi == '') {
			// 拡張子が無い
			return false;
		} else {
			// 一番最後の拡張子を対象にcsvチェック
			if (!kakutyoshi.match(/(csv)$/i)) {
				return false;
			}
		}
	}
	return true;
	},ART0027
);



