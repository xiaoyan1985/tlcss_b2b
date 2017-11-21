//********************
//	共通変数（固定値）
//********************
han_kana="｡｢｣､･ｦｧｨｩｪｫｬｭｮｯｰｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜﾝﾞﾟ 0123456789";	//***半角カナ文字設定***
han_num="0123456789/:";																											//***半角数字設定（日付・時間対応）***
zen_num="０１２３４５６７８９／：";																					//***全角数字設定（日付・時間対応）***
zen_num_hai="０１２３４５６７８９－ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ";	//***全角数字全角マイナス全角英字大文字***
han_digit="0123456789";																											//***半角数字設定***
han_alpha="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";						//***半角アルファベット設定***
han_symbol="!##$%&'()*+,-./:;<=>?@[\\]^_`{|}~[] ";														//***半角記号設定***
han_symbol=han_symbol+'"';
byte2_set="×";
han_meigi="ｦｧｨｩｪｫｬｭｮｯｰｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜﾝﾞﾟ()0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ･-\\,.｢｣/ ";		//***** 半角銀行名義人用文字設定 *****
zen_kana_for_convert = ["ァ","ア","ィ","イ","ゥ","ウ","ェ","エ","ォ","オ","カ","ガ","キ","ギ","ク","グ","ケ","ゲ","コ","ゴ","サ","ザ","シ","ジ","ス","ズ","セ","ゼ","ソ","ゾ","タ","ダ","チ","ヂ","ッ","ツ","ヅ","テ","デ","ト","ド","ナ","ニ","ヌ","ネ","ノ","ハ","バ","パ","ヒ","ビ","ピ","フ","ブ","プ","ヘ","ベ","ペ","ホ","ボ","ポ","マ","ミ","ム","メ","モ","ャ","ヤ","ュ","ユ","ョ","ヨ","ラ","リ","ル","レ","ロ","ワ","ヲ","ン","ヴ","ー","―","－","‐", String.fromCharCode(8722)];
han_kana_for_convert = ["ｧ","ｱ","ｨ","ｲ","ｩ","ｳ","ｪ","ｴ","ｫ","ｵ","ｶ","ｶﾞ","ｷ","ｷﾞ","ｸ","ｸﾞ","ｹ","ｹﾞ","ｺ","ｺﾞ","ｻ","ｻﾞ","ｼ","ｼﾞ","ｽ","ｽﾞ","ｾ","ｾﾞ","ｿ","ｿﾞ","ﾀ","ﾀﾞ","ﾁ","ﾁﾞ","ｯ","ﾂ","ﾂﾞ","ﾃ","ﾃﾞ","ﾄ","ﾄﾞ","ﾅ","ﾆ","ﾇ","ﾈ","ﾉ","ﾊ","ﾊﾞ","ﾊﾟ","ﾋ","ﾋﾞ","ﾋﾟ","ﾌ","ﾌﾞ","ﾌﾟ","ﾍ","ﾍﾞ","ﾍﾟ","ﾎ","ﾎﾞ","ﾎﾟ","ﾏ","ﾐ","ﾑ","ﾒ","ﾓ","ｬ","ﾔ","ｭ","ﾕ","ｮ","ﾖ","ﾗ","ﾘ","ﾙ","ﾚ","ﾛ","ﾜ","ｦ","ﾝ","ｳﾞ","ｰ","-","-","-","-"];

//*******************************************************
//全角文字チェック
//引数	：dat
//戻り値：1:全角文字のみ 0:全角文字以外
//概要	：全角文字のみかそれ以外かチェック
//        ※tab文字は入力不可
//*******************************************************
function ZenChk(dat){
	for (i=0; i<dat.length; i++){
		//半角数字 半角カナ 半角アルファベット 半角記号 いずれかに当てはまる場合
		if(han_digit.indexOf(dat.charAt(i),0) >= 0 || han_kana.indexOf(dat.charAt(i),0) >= 0 ||
			han_alpha.indexOf(dat.charAt(i),0) >= 0 || han_symbol.indexOf(dat.charAt(i),0) >= 0){
			return 0;
		}
	}

	if(TabChk(dat)==true){
		return 0;
	}

	return 1;
}
//*******************************************************
//半角文字チェック
//引数	：dat
//戻り値：1:半角文字のみ 0:半角文字以外
//概要	：半角文字のみかそれ以外かチェック
//*******************************************************
function HanChk(dat){
	for (i=0; i<dat.length; i++){
		//半角数字 半角カナ 半角アルファベット 半角記号 すべてに当てはまらない場合
		if(han_digit.indexOf(dat.charAt(i),0) < 0 && han_kana.indexOf(dat.charAt(i),0) < 0 &&
			han_alpha.indexOf(dat.charAt(i),0) < 0 && han_symbol.indexOf(dat.charAt(i),0) < 0){
			return 0;
		}
	}
	return 1;
}
//**********************************************************
//全角数字を半角数字に変換
//引数：str:文字列
//戻り値：str2:変換後文字列
//概要：日付、時間対応(:/：／)
//			文字列中に数字以外の文字がある場合も変換する
//**********************************************************
function ZenToHan_Num(str){
	str2="";

	for(i=0;i<str.length;i++){
		c=str.charAt(i);
		n=zen_num.indexOf(c,0);
		if(n>=0){
			c=han_num.charAt(n);
		}
		str2+=c;
	}
	return str2;
}
//**********************************************************
//日付チェック データ(yyyy/mm/dd,yyyy/m/d)の妥当性
//引 数：bf:文字列(yyyy/mm/dd,yyyy/m/d)
//戻り値：正常：文字列(yyyy/mm/dd) 異常：-1
//概要：全角入力データは半角データに変換
//			ZenToHan_Num()関数を利用
//**********************************************************
function DateChk(bf){
	var str;
	var y="";
	var m="";
	var d="";
	var i;

	//全角入力データは半角データに変換
	bf = ZenToHan_Num(bf);

	//年
	y = bf.substring(0,bf.indexOf("/"));
	//４桁でない時はエラー
	if(y.length != 4){
			return -1;
	}
	//数値とスラッシュ以外ならばエラー
	if(NamChk_slash(y)==false){
		return -1;
	}

	//月
	m = bf.substring(bf.indexOf("/")+1,bf.lastIndexOf("/"));
	//もし一桁ならば前ゼロをつける
	if(m.length == 1){
		m = "0" + m;
	//二桁ならばそのまま
	}else if(m.length == 2){
		m = m;
	//それ以外ならばエラー
	}else{
			return -1;
	}
	//数値とスラッシュ以外ならばエラー
	if(NamChk_slash(m)==false){
		return -1;
	}

	//日
	d = bf.substring(bf.lastIndexOf("/")+1);
	//もし一桁ならば前ゼロをつける
	if(d.length == 1){
		d = "0" + d;
	//二桁ならばそのまま
	}else if(d.length == 2){
		d = d;
	//それ以外ならばエラー
	}else{
			return -1;
	}
	//数値とスラッシュ以外ならばエラー
	if(NamChk_slash(d)==false){
		return -1;
	}

	Dobj = new Date(y,m - 1,d);
	if(Dobj.getFullYear() < 1900) y -= 1900;
	if((eval(y) != Dobj.getFullYear()) || (eval(m) - 1 != Dobj.getMonth()) ||
		 (eval(d) != Dobj.getDate())){
		return -1;
	}

	str = y + "/" + m + "/" + d;
	return str;
}

//**********************************************************
//日付チェック データ(yyyymmdd)の妥当性
//引数：str:文字列(yyyymmdd)
//戻り値：正常：文字列(yyyymmdd) 異常：-1
//概要：全角入力データは半角データに変換
//			ZenToHan_Num()関数を利用
//**********************************************************
function DateChk2(str){

	if(str.length == 8){

		str = ZenToHan_Num(str);
		str_yy = str.substring(0,4);
		str_mm = str.substring(4,6);
		str_dd = str.substring(6,8);

		//正しい日付か？
		Dobj = new Date(str_yy,str_mm - 1,str_dd);
		if(Dobj.getYear() < 1900) str_yy -= 1900;
		if((str_yy != Dobj.getYear()) ||
			(str_mm - 1 != Dobj.getMonth()) ||
			(str_dd != Dobj.getDate())){
			return -1;
		}
	} else {
		return -1;
	}
	return str;
}

//**********************************************************
//日付チェック データ(yyyymm)の妥当性
//引数：str:文字列(yyyymm)
//戻り値：正常：文字列(yyyymm) 異常：-1
//概要：全角入力データは半角データに変換
//			ZenToHan_Num()関数を利用
//**********************************************************
function DateChk3(str){

	if(str.length == 6){

		str = ZenToHan_Num(str);
		str_yy = str.substring(0,4);
		str_mm = str.substring(4,6);

		//正しい日付か？
		Dobj = new Date(str_yy,str_mm - 1);
		if(Dobj.getYear() < 1900) str_yy -= 1900;
		if((str_yy != Dobj.getYear()) ||
			(str_mm - 1 != Dobj.getMonth())){
			return -1;
		}
	} else {
		return -1;
	}
	return str;
}
//**********************************************************
//日付チェック データ(yyyy/mm,yyyy/m)の妥当性
//引 数：bf:文字列(yyyy/mm,yyyy/m)
//戻り値：正常：文字列(yyyy/mm) 異常：-1
//概要：全角入力データは半角データに変換
//			ZenToHan_Num()関数を利用
//**********************************************************
function DateChk4(bf){
	var str;
	var y="";
	var m="";
	var i;

	//全角入力データは半角データに変換
	bf = ZenToHan_Num(bf);

	//年
	y = bf.substring(0,bf.indexOf("/"));
	//４桁でない時はエラー
	if(y.length != 4){
			return -1;
	}
	//数値とスラッシュ以外ならばエラー
	if(NamChk_slash(y)==false){
		return -1;
	}

	//月
	m = bf.substring(bf.lastIndexOf("/")+1);
	//もし一桁ならば前ゼロをつける
	if(m.length == 1){
		m = "0" + m;
	//二桁ならばそのまま
	}else if(m.length == 2){
		m = m;
	//それ以外ならばエラー
	}else{
			return -1;
	}
	//数値とスラッシュ以外ならばエラー
	if(NamChk_slash(m)==false){
		return -1;
	}

	Dobj = new Date(y,m - 1);
	if(Dobj.getFullYear() < 1900) y -= 1900;
	if((eval(y) != Dobj.getFullYear()) || (eval(m) - 1 != Dobj.getMonth())){
		return -1;
	}

	str = y + "/" + m;
	return str;
}
//************************************************************************
//数値チェック(IE)+'-'
//引数	：bf
//戻り値：true:半角数字とマイナス記号・ハイフンのみ
//				false:半角数字とマイナス記号・ハイフン以外
//概要	：半角数字とマイナス記号・ハイフンのみかそれ以外かチェック
//************************************************************************
function NamChk_minus(bf){
	var i;

	for(i=0;i<bf.length; i++){
		if(bf.charAt(i)!="-"){
			if(bf.charAt(i)<"0" || bf.charAt(i)>"9"){
				break;
			}
		}
	}
	if(i<bf.length){
		return false;
	}
	return true;
}
//**********************************************************
//小数値チェック2
//引 数：value:入力された値
//戻り値：正常：true 異常：false
//概要：入力フォーマットが「○」か[○.○]になっているか判定
//更新：
//**********************************************************
function decimalChk2(value) {
	if (value.match(/^[0-9]+$|[0-9]+\.[0-9]+$/)) {
		return true;
	}
	return false;
}
//*************************************************************************************
//整数の桁数チェック
//引数　：value：チェックする値
//引数　：seisu：整数桁数上限
//戻り値：整数の桁数が引き渡した桁数上限以下の場合、true
//        それ以外はfalse
//概要　：
//*************************************************************************************
function fnc_chk_seisu(value, seisu) {

	if(NamChk_IE(seisu)) {
		seisuKeta = seisu;
	} else {
		return false;
	}

	if (isNaN(value) == true){			//数値チェック
		 return false;
	}else{
		//整数部小数部の切り分け
		if ((""+value).indexOf(".") > -1){
			ketaDec1=value.split(".")[0];
			ketaDec2=value.split(".")[1];
		}else{
			ketaDec1=value;
			ketaDec2="";
		}

		if (Get_Byte_Kana(ketaDec1)>seisuKeta || NamChk_IE(ketaDec1) == false) {	//マイナス無し整数部チェック
			return false;
		}
	}
	return true;
}

//********************************************************************************************
//バイト数取得（半角カナを1バイトと数える）
//引数	：dat
//戻り値：len
//概要	：引数datの先頭から引数byteﾊﾞｲﾄまでの文字列を返す
//×を1バイト文字と判別していたため修正。
//今後もしこのようなパターンの文字が発生したら、byte2_setに該当文字を追加する。
//********************************************************************************************
function Get_Byte_Kana(dat){
	var len=0;
	for(j=0;j<dat.length;j++){
		d=dat.charAt(j);
		if(byte2_set.indexOf(d,0) >= 0){	//2バイト設定文字に当てはまる場合
			len+=2;
		}else{						//通常文字列
			d1=escape(d);
			if(Byte_KanaChk(d)==1){
				len+=1;
			}else{
				if(d1.length<4){
					len+=1;
				}else{
					len+=2;
				}
			}
		}
	}
	return len;
}

function Byte_KanaChk(dat){
	for (i=0; i<dat.length; i++){
		if(han_kana.indexOf(dat.charAt(i),0) >= 0){
			return 1;
		}
	}
	return 0;
}
//*******************************************************
//数値チェック(IE)
//引数	：bf
//戻り値：true:半角数字のみ false:半角数字以外
//概要	：半角数字のみかそれ以外かチェック
//*******************************************************
function NamChk_IE(bf){
	var i;

	for(i=0;i<String(bf).length;i++){
		if(String(bf).charAt(i)<"0" || String(bf).charAt(i)>"9"){
			break;
		}
	}
	if(i<String(bf).length){
		return false;
	}
	return true;
}

//********************************************************************
//数値チェック(IE)+'/'
//引数	：bf
//戻り値：true:半角数字とスラッシュのみ false:半角数字とスラッシュ以外
//概要	：半角数字とスラッシュのみかそれ以外かチェック
//********************************************************************
function NamChk_slash(bf){
	var i;

	for(i=0;i<bf.length;i++){
		if(bf.charAt(i)!="/"){
			if(bf.charAt(i)<"0" || bf.charAt(i)>"9"){
				break;
			}
		}
	}
	if(i<bf.length){
		return false;
	}
	return true;
}

//********************************************************************
//数値チェック(IE)+':'
//引数	：bf
//戻り値：true:半角数字とコロンのみ false:半角数字とコロン以外
//概要	：半角数字とコロンのみかそれ以外かチェック
//********************************************************************
function NamChk_colon(bf){
	var i;

	for(i=0;i<bf.length;i++){
		if(bf.charAt(i)!=":"){
			if(bf.charAt(i)<"0" || bf.charAt(i)>"9"){
				break;
			}
		}
	}
	if(i<bf.length){
		return false;
	}
	return true;
}

//*************************************************************************************
//小数の桁数チェック
//引数　：value：チェックする値
//引数　：seisu：小数桁数上限
//戻り値：小数の桁数が引き渡した桁数上限以下の場合、true
//        それ以外はfalse
//概要　：
//*************************************************************************************
function fnc_chk_shosu2(value, shosu) {

	if(NamChk_IE(shosu)) {
		shosuuKeta = shosu;
	} else {
		return false;
	}

	if (isNaN(value) == true){			//数値チェック
		 return false;
	}else{
		//整数部小数部の切り分け
		if ((""+value).indexOf(".") > -1){
			ketaDec1=value.split(".")[0];
			ketaDec2=value.split(".")[1];
		}else{
			ketaDec1=value;
			ketaDec2="";
		}


		if(Get_Byte_Kana(ketaDec2)>shosuuKeta){		//小数部チェック
			return false;
		}
	}
	return true;
}
//**********************************************************
//時間チェック データ(hh:mm)の妥当性
//引数：bf:文字列(hh:mm) nm:項目名
//戻り値：正常：文字列(hh:mm) 異常：-1
//概要：全角入力データは半角データに変換
//			ZenToHan_Num()関数を利用
//**********************************************************
function TimeChk(bf){
	var str;
	var h="";
	var m="";
	var i;

	bf = ZenToHan_Num(bf);

	//時
	h = bf.substring(0,bf.indexOf(":"));
	//もし一桁ならば前ゼロをつける
	if(h.length == 1){
		h = "0" + h;
	//二桁ならばそのまま
	}else if(h.length == 2){
		h = h;
	//それ以外ならばエラー
	}else{
			return -1;
	}
	//数値とコロン以外ならばエラー
	if(NamChk_colon(h)==false){
		return -1;
	}

	//分
	m = bf.substring(bf.lastIndexOf(":")+1);
	//もし一桁ならば前ゼロをつける
	if(m.length == 1){
		m = "0" + m;
	//二桁ならばそのまま
	}else if(m.length == 2){
		m = m;
	//それ以外ならばエラー
	}else{
			return -1;
	}
	//数値とコロン以外ならばエラー
	if(NamChk_colon(m)==false){
		return -1;
	}

	//正しい時間か？
	if(eval(h) < 0 || eval(h) > 23 || eval(m) < 0 || eval(m) > 59){
			return -1;
	}

	str = h + ":" + m;
	return str;
}

//**********************************************************
//時間チェック データ(hhmm)の妥当性
//引数：str:文字列(hhmm)
//戻り値：正常：文字列(hhmm) 異常：-1
//概要：全角入力データは半角データに変換
//			ZenToHan_Num()関数を利用
//**********************************************************
function TimeChk2(str) {

	str2 = "";

	if (str.length == 4 || str.length == 3) {
		str = ZenToHan_Num(str);
		if(str.length==3){
			// 3桁の場合先頭1文字を時間に設定する。
			str_hh = str.substring(0,1);
			str_mm = str.substring(1,3);
		}else{
			str_hh = str.substring(0,2);
			str_mm = str.substring(2,4);
		}
		if (((str_hh >= 0 && str_hh < 24) && (str_mm >= 0 && str_mm <= 59))) {
			return (str_hh + str_mm);
		} else {
			return -1;
		}
	} else {
		return -1;
	}
}

//********************************************************************
//日付の「/」編集（半角）
//引数	：Instr:文字列 mode:フラグ
//戻り値：Outstr:「/」編集 Instr:変換不可能の場合
//概要	：数字以外の場合は、引数をそのまま返す
//				modeはYYYY/MM/DDかYYYY/MMかのフラグ
//				1:YYYY/MM/DD , 2:YYYY/MM
//				Get_Byte_Kana()関数を利用
//********************************************************************
function ConvSlash(Instr,mode) {
	var Outstr;
	var cnt;
	Outstr = "";
	Sign_Flg = 0;
	cnt = 0;

	ret=Get_Byte_Kana(Instr);		//バイト数取得
	if(mode==1 && ret!=8 || mode==2 && ret !=6){
		return(Instr);
	}

	Instr = String(Instr);
	if (Instr==null || Instr.length==0) {
		return("");
	}
	else {
		for (i=Instr.length-1; i>=0; i--) {
			if (escape("0")<=escape(Instr.charAt(i)) && escape(Instr.charAt(i))<=escape("9")) {
				switch (mode){
					case 1:
						if (cnt==2) {
							Outstr = Instr.charAt(i) + "/" + Outstr;
						}
						else if (cnt==4){
							Outstr = Instr.charAt(i) + "/" + Outstr;
						}
						else{
							Outstr = Instr.charAt(i) + Outstr;
						}
						break;
					case 2:
						if (cnt==2) {
							Outstr = Instr.charAt(i) + "/" + Outstr;
						}
						else{
							Outstr = Instr.charAt(i) + Outstr;
						}
						break;
				}
			}
			else {
				return(Instr);
			}
			cnt = cnt + 1;
		}
		return(Outstr);
	}
}

//********************************************************************
//日付の「/」削除（半角）
//引数	：Instr:文字列
//戻り値：Outstr:「/」編集 Instr:変換不可能の場合
//概要	：数字以外の場合は、引数をそのまま返す
//********************************************************************
function ConvDelSlash(Instr) {
	var Outstr;
	Outstr = "";
	Sign_Flg = 0;

	Instr = String(Instr);
	if (Instr==null || Instr.length==0) {
		return("");
	}
	else {
		for (i=Instr.length-1; i>=0; i--) {
			if (escape("/")==escape(Instr.charAt(i))){
				Outstr = Outstr;
			}
			else{
				Outstr = Instr.charAt(i) + Outstr;
			}
		}
		return(Outstr);
	}
}

//********************************************************************
//日付ゼロ編集
//引数	：dt:文字列
//戻り値：YYYY/M/D or YYYY/MM/D or YYYY/M/DD を YYYY/MM/DDに変換した文字列
//********************************************************************
function ConvZeroYMD(dt) {
	var padingDt = dt;
	// YYYY/M/D YYYY/MM/D or YYYY/M/DD
	if (dt.length != 8 && dt.length != 9) {
		return padingDt;
	}
	if (dt.indexOf("/") != -1) {
		dtArray = dt.split("/");
		padingDt = "";
		for (var i = 0; i < dtArray.length; i++) {
			if (i == 0) {
				padingDt += ConvZero(dtArray[i], 4);
			} else {
				padingDt += ConvZero(dtArray[i], 2);
			}
		}
	}
	return padingDt;
}
//********************************************************************
//金額のカンマ削除
//引数	：place:金額
//戻り値：d:金額
//概要	：値のカンマ","を削除する（金額表示）
//********************************************************************
function delComma(place){
	d = place.replace(/,/g,"");
	return d;
}

//********************************************************************
//Date型変換処理
//引数	：date:日付
//戻り値：d:Dateオブジェクト、日付として異常な場合はnull
//概要	：文字列からDate型に変換する
//********************************************************************
function convertDate(date) {
	var dateObj;
	var dateYear = "";
	var dateMonth = "";
	var dateDay = "";

	if (DateChk(date) != -1) {
		// yyyy/mm/dd形式、yyyy/m/d形式の場合
		dateYear = date.split("/")[0];
		dateMonth = date.split("/")[1];
		dateDay = date.split("/")[2];

		dateObj = new Date(dateYear, parseInt(dateMonth, 10) - 1, dateDay);
	} else if (DateChk2(date) != -1) {
		// yyyyymmdd形式の場合
		dateYear = date.substring(0, 4);
		dateMonth = date.substring(4, 6);
		dateDay = date.substring(6, 8);

		dateObj = new Date(dateYear, parseInt(dateMonth) - 1, dateDay);
	} else if (DateChk3(date) != -1) {
		// yyyy/mm、yyyy/m形式の場合
		dateYear = date.split("/")[0];
		dateMonth = date.split("/")[1];

		dateObj = new Date(dateYear, parseInt(dateMonth, 10) - 1, 1);
	} else if (DateChk4(date) != -1) {
		// yyyymm形式の場合
		dateYear = date.substring(0, 4);
		dateMonth = date.substring(4, 6);

		dateObj = new Date(dateYear, parseInt(dateMonth) - 1, 1);
	} else {
		// 上記以外は、処理終了
		return null;
	}

	return dateObj;
}

//********************************************************************
//時間の「:」編集（半角）
//引数	：Instr:文字列
//戻り値：Outstr:「/」編集 Instr:変換不可能の場合
//概要	：数字以外の場合は、引数をそのまま返す
//				Get_Byte_Kana()関数を利用
//********************************************************************
function ConvCoron(Instr) {
	var Outstr;
	var cnt;
	Outstr = "";
	Sign_Flg = 0;
	cnt = 0;

	ret=Get_Byte_Kana(Instr);		// バイト数取得
	if(ret != 3 && ret != 4){		// 3バイトも許容。time属性でチェックエラーにならない[059]などを考慮。
		return(Instr);
	}
	
	Instr = String(Instr);
	
	// 数字のみ3桁ならば、先頭を"0"で補完
	if (!Instr.match(/[^0-9]+/) && Instr.length == 3) {
		Instr = "0" + Instr;
	}
	
	if (Instr==null || Instr.length==0) {
		return("");
	}else {
		for (i=Instr.length-1; i>=0; i--) {
			if (escape("0")<=escape(Instr.charAt(i)) && escape(Instr.charAt(i))<=escape("9")) {
				if (cnt==2) {
					Outstr = Instr.charAt(i) + ":" + Outstr;
				}else{
					Outstr = Instr.charAt(i) + Outstr;
				}
			}else {
				return(Instr);
			}
			cnt = cnt + 1;
		}
		return(Outstr);
	}
}

//********************************************************************
//時間の「:」削除（半角）
//引数	：Instr:文字列
//戻り値：Outstr:「/」編集 Instr:変換不可能の場合
//概要	：数字以外の場合は、引数をそのまま返す
//********************************************************************
function ConvDelCoron(Instr) {
	var Outstr;
	Outstr = "";
	Sign_Flg = 0;

	Instr = String(Instr);
	if (Instr==null || Instr.length==0) {
		return("");
	}
	else {
		for (i=Instr.length-1; i>=0; i--) {
			if (escape(":")==escape(Instr.charAt(i))){
				Outstr = Outstr;
			}
			else{
				Outstr = Instr.charAt(i) + Outstr;
			}
		}
		return(Outstr);
	}
}

//*******************************************************
//エラーメッセージ編集
//引数	：str sMsg：元になるメッセージ
//	：str sRep1：sMsg内の%1%と置き換える文字列
//	：str sRep2：sMsg内の%2%と置き換える文字列
//	：str sRep3：sMsg内の%3%と置き換える文字列
//戻り値：str
//概要	：元メッセージを置換文字列で編集し返す。
//更新：
//*******************************************************
function fncMakeErrMsg(sMsg, sRep1, sRep2, sRep3){
	var strErrMsg="";

	strErrMsg=sMsg;
	while(strErrMsg.match("%1%")){
		strErrMsg=strErrMsg.replace("%1%", sRep1);
	}
	while(strErrMsg.match("%2%")){
		strErrMsg=strErrMsg.replace("%2%", sRep2);
	}
	while(strErrMsg.match("%3%")){
		strErrMsg=strErrMsg.replace("%3%", sRep3);
	}
	return strErrMsg;
}
