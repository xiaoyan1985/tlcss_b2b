//********************
//	���ʕϐ��i�Œ�l�j
//********************
han_kana="��������������������������������������������������������������� 0123456789";	//***���p�J�i�����ݒ�***
han_num="0123456789/:";																											//***���p�����ݒ�i���t�E���ԑΉ��j***
zen_num="�O�P�Q�R�S�T�U�V�W�X�^�F";																					//***�S�p�����ݒ�i���t�E���ԑΉ��j***
zen_num_hai="�O�P�Q�R�S�T�U�V�W�X�|�`�a�b�c�d�e�f�g�h�i�j�k�l�m�n�o�p�q�r�s�t�u�v�w�x�y";	//***�S�p�����S�p�}�C�i�X�S�p�p���啶��***
han_digit="0123456789";																											//***���p�����ݒ�***
han_alpha="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";						//***���p�A���t�@�x�b�g�ݒ�***
han_symbol="!##$%&'()*+,-./:;<=>?@[\\]^_`{|}~[] ";														//***���p�L���ݒ�***
han_symbol=han_symbol+'"';
byte2_set="�~";
han_meigi="����������������������������������������������������������()0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ�-\\,.��/ ";		//***** ���p��s���`�l�p�����ݒ� *****
zen_kana_for_convert = ["�@","�A","�B","�C","�D","�E","�F","�G","�H","�I","�J","�K","�L","�M","�N","�O","�P","�Q","�R","�S","�T","�U","�V","�W","�X","�Y","�Z","�[","�\","�]","�^","�_","�`","�a","�b","�c","�d","�e","�f","�g","�h","�i","�j","�k","�l","�m","�n","�o","�p","�q","�r","�s","�t","�u","�v","�w","�x","�y","�z","�{","�|","�}","�~","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","��","�[","�\","�|","�]", String.fromCharCode(8722)];
han_kana_for_convert = ["�","�","�","�","�","�","�","�","�","�","�","��","�","��","�","��","�","��","�","��","�","��","�","��","�","��","�","��","�","��","�","��","�","��","�","�","��","�","��","�","��","�","�","�","�","�","�","��","��","�","��","��","�","��","��","�","��","��","�","��","��","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","�","��","�","-","-","-","-"];

//*******************************************************
//�S�p�����`�F�b�N
//����	�Fdat
//�߂�l�F1:�S�p�����̂� 0:�S�p�����ȊO
//�T�v	�F�S�p�����݂̂�����ȊO���`�F�b�N
//        ��tab�����͓��͕s��
//*******************************************************
function ZenChk(dat){
	for (i=0; i<dat.length; i++){
		//���p���� ���p�J�i ���p�A���t�@�x�b�g ���p�L�� �����ꂩ�ɓ��Ă͂܂�ꍇ
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
//���p�����`�F�b�N
//����	�Fdat
//�߂�l�F1:���p�����̂� 0:���p�����ȊO
//�T�v	�F���p�����݂̂�����ȊO���`�F�b�N
//*******************************************************
function HanChk(dat){
	for (i=0; i<dat.length; i++){
		//���p���� ���p�J�i ���p�A���t�@�x�b�g ���p�L�� ���ׂĂɓ��Ă͂܂�Ȃ��ꍇ
		if(han_digit.indexOf(dat.charAt(i),0) < 0 && han_kana.indexOf(dat.charAt(i),0) < 0 &&
			han_alpha.indexOf(dat.charAt(i),0) < 0 && han_symbol.indexOf(dat.charAt(i),0) < 0){
			return 0;
		}
	}
	return 1;
}
//**********************************************************
//�S�p�����𔼊p�����ɕϊ�
//�����Fstr:������
//�߂�l�Fstr2:�ϊ��㕶����
//�T�v�F���t�A���ԑΉ�(:/�F�^)
//			�����񒆂ɐ����ȊO�̕���������ꍇ���ϊ�����
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
//���t�`�F�b�N �f�[�^(yyyy/mm/dd,yyyy/m/d)�̑Ó���
//�� ���Fbf:������(yyyy/mm/dd,yyyy/m/d)
//�߂�l�F����F������(yyyy/mm/dd) �ُ�F-1
//�T�v�F�S�p���̓f�[�^�͔��p�f�[�^�ɕϊ�
//			ZenToHan_Num()�֐��𗘗p
//**********************************************************
function DateChk(bf){
	var str;
	var y="";
	var m="";
	var d="";
	var i;

	//�S�p���̓f�[�^�͔��p�f�[�^�ɕϊ�
	bf = ZenToHan_Num(bf);

	//�N
	y = bf.substring(0,bf.indexOf("/"));
	//�S���łȂ����̓G���[
	if(y.length != 4){
			return -1;
	}
	//���l�ƃX���b�V���ȊO�Ȃ�΃G���[
	if(NamChk_slash(y)==false){
		return -1;
	}

	//��
	m = bf.substring(bf.indexOf("/")+1,bf.lastIndexOf("/"));
	//�����ꌅ�Ȃ�ΑO�[��������
	if(m.length == 1){
		m = "0" + m;
	//�񌅂Ȃ�΂��̂܂�
	}else if(m.length == 2){
		m = m;
	//����ȊO�Ȃ�΃G���[
	}else{
			return -1;
	}
	//���l�ƃX���b�V���ȊO�Ȃ�΃G���[
	if(NamChk_slash(m)==false){
		return -1;
	}

	//��
	d = bf.substring(bf.lastIndexOf("/")+1);
	//�����ꌅ�Ȃ�ΑO�[��������
	if(d.length == 1){
		d = "0" + d;
	//�񌅂Ȃ�΂��̂܂�
	}else if(d.length == 2){
		d = d;
	//����ȊO�Ȃ�΃G���[
	}else{
			return -1;
	}
	//���l�ƃX���b�V���ȊO�Ȃ�΃G���[
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
//���t�`�F�b�N �f�[�^(yyyymmdd)�̑Ó���
//�����Fstr:������(yyyymmdd)
//�߂�l�F����F������(yyyymmdd) �ُ�F-1
//�T�v�F�S�p���̓f�[�^�͔��p�f�[�^�ɕϊ�
//			ZenToHan_Num()�֐��𗘗p
//**********************************************************
function DateChk2(str){

	if(str.length == 8){

		str = ZenToHan_Num(str);
		str_yy = str.substring(0,4);
		str_mm = str.substring(4,6);
		str_dd = str.substring(6,8);

		//���������t���H
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
//���t�`�F�b�N �f�[�^(yyyymm)�̑Ó���
//�����Fstr:������(yyyymm)
//�߂�l�F����F������(yyyymm) �ُ�F-1
//�T�v�F�S�p���̓f�[�^�͔��p�f�[�^�ɕϊ�
//			ZenToHan_Num()�֐��𗘗p
//**********************************************************
function DateChk3(str){

	if(str.length == 6){

		str = ZenToHan_Num(str);
		str_yy = str.substring(0,4);
		str_mm = str.substring(4,6);

		//���������t���H
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
//���t�`�F�b�N �f�[�^(yyyy/mm,yyyy/m)�̑Ó���
//�� ���Fbf:������(yyyy/mm,yyyy/m)
//�߂�l�F����F������(yyyy/mm) �ُ�F-1
//�T�v�F�S�p���̓f�[�^�͔��p�f�[�^�ɕϊ�
//			ZenToHan_Num()�֐��𗘗p
//**********************************************************
function DateChk4(bf){
	var str;
	var y="";
	var m="";
	var i;

	//�S�p���̓f�[�^�͔��p�f�[�^�ɕϊ�
	bf = ZenToHan_Num(bf);

	//�N
	y = bf.substring(0,bf.indexOf("/"));
	//�S���łȂ����̓G���[
	if(y.length != 4){
			return -1;
	}
	//���l�ƃX���b�V���ȊO�Ȃ�΃G���[
	if(NamChk_slash(y)==false){
		return -1;
	}

	//��
	m = bf.substring(bf.lastIndexOf("/")+1);
	//�����ꌅ�Ȃ�ΑO�[��������
	if(m.length == 1){
		m = "0" + m;
	//�񌅂Ȃ�΂��̂܂�
	}else if(m.length == 2){
		m = m;
	//����ȊO�Ȃ�΃G���[
	}else{
			return -1;
	}
	//���l�ƃX���b�V���ȊO�Ȃ�΃G���[
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
//���l�`�F�b�N(IE)+'-'
//����	�Fbf
//�߂�l�Ftrue:���p�����ƃ}�C�i�X�L���E�n�C�t���̂�
//				false:���p�����ƃ}�C�i�X�L���E�n�C�t���ȊO
//�T�v	�F���p�����ƃ}�C�i�X�L���E�n�C�t���݂̂�����ȊO���`�F�b�N
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
//�����l�`�F�b�N2
//�� ���Fvalue:���͂��ꂽ�l
//�߂�l�F����Ftrue �ُ�Ffalse
//�T�v�F���̓t�H�[�}�b�g���u���v��[��.��]�ɂȂ��Ă��邩����
//�X�V�F
//**********************************************************
function decimalChk2(value) {
	if (value.match(/^[0-9]+$|[0-9]+\.[0-9]+$/)) {
		return true;
	}
	return false;
}
//*************************************************************************************
//�����̌����`�F�b�N
//�����@�Fvalue�F�`�F�b�N����l
//�����@�Fseisu�F�����������
//�߂�l�F�����̌����������n������������ȉ��̏ꍇ�Atrue
//        ����ȊO��false
//�T�v�@�F
//*************************************************************************************
function fnc_chk_seisu(value, seisu) {

	if(NamChk_IE(seisu)) {
		seisuKeta = seisu;
	} else {
		return false;
	}

	if (isNaN(value) == true){			//���l�`�F�b�N
		 return false;
	}else{
		//�������������̐؂蕪��
		if ((""+value).indexOf(".") > -1){
			ketaDec1=value.split(".")[0];
			ketaDec2=value.split(".")[1];
		}else{
			ketaDec1=value;
			ketaDec2="";
		}

		if (Get_Byte_Kana(ketaDec1)>seisuKeta || NamChk_IE(ketaDec1) == false) {	//�}�C�i�X�����������`�F�b�N
			return false;
		}
	}
	return true;
}

//********************************************************************************************
//�o�C�g���擾�i���p�J�i��1�o�C�g�Ɛ�����j
//����	�Fdat
//�߂�l�Flen
//�T�v	�F����dat�̐擪�������byte�޲Ă܂ł̕������Ԃ�
//�~��1�o�C�g�����Ɣ��ʂ��Ă������ߏC���B
//����������̂悤�ȃp�^�[���̕���������������Abyte2_set�ɊY��������ǉ�����B
//********************************************************************************************
function Get_Byte_Kana(dat){
	var len=0;
	for(j=0;j<dat.length;j++){
		d=dat.charAt(j);
		if(byte2_set.indexOf(d,0) >= 0){	//2�o�C�g�ݒ蕶���ɓ��Ă͂܂�ꍇ
			len+=2;
		}else{						//�ʏ핶����
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
//���l�`�F�b�N(IE)
//����	�Fbf
//�߂�l�Ftrue:���p�����̂� false:���p�����ȊO
//�T�v	�F���p�����݂̂�����ȊO���`�F�b�N
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
//���l�`�F�b�N(IE)+'/'
//����	�Fbf
//�߂�l�Ftrue:���p�����ƃX���b�V���̂� false:���p�����ƃX���b�V���ȊO
//�T�v	�F���p�����ƃX���b�V���݂̂�����ȊO���`�F�b�N
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
//���l�`�F�b�N(IE)+':'
//����	�Fbf
//�߂�l�Ftrue:���p�����ƃR�����̂� false:���p�����ƃR�����ȊO
//�T�v	�F���p�����ƃR�����݂̂�����ȊO���`�F�b�N
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
//�����̌����`�F�b�N
//�����@�Fvalue�F�`�F�b�N����l
//�����@�Fseisu�F�����������
//�߂�l�F�����̌����������n������������ȉ��̏ꍇ�Atrue
//        ����ȊO��false
//�T�v�@�F
//*************************************************************************************
function fnc_chk_shosu2(value, shosu) {

	if(NamChk_IE(shosu)) {
		shosuuKeta = shosu;
	} else {
		return false;
	}

	if (isNaN(value) == true){			//���l�`�F�b�N
		 return false;
	}else{
		//�������������̐؂蕪��
		if ((""+value).indexOf(".") > -1){
			ketaDec1=value.split(".")[0];
			ketaDec2=value.split(".")[1];
		}else{
			ketaDec1=value;
			ketaDec2="";
		}


		if(Get_Byte_Kana(ketaDec2)>shosuuKeta){		//�������`�F�b�N
			return false;
		}
	}
	return true;
}
//**********************************************************
//���ԃ`�F�b�N �f�[�^(hh:mm)�̑Ó���
//�����Fbf:������(hh:mm) nm:���ږ�
//�߂�l�F����F������(hh:mm) �ُ�F-1
//�T�v�F�S�p���̓f�[�^�͔��p�f�[�^�ɕϊ�
//			ZenToHan_Num()�֐��𗘗p
//**********************************************************
function TimeChk(bf){
	var str;
	var h="";
	var m="";
	var i;

	bf = ZenToHan_Num(bf);

	//��
	h = bf.substring(0,bf.indexOf(":"));
	//�����ꌅ�Ȃ�ΑO�[��������
	if(h.length == 1){
		h = "0" + h;
	//�񌅂Ȃ�΂��̂܂�
	}else if(h.length == 2){
		h = h;
	//����ȊO�Ȃ�΃G���[
	}else{
			return -1;
	}
	//���l�ƃR�����ȊO�Ȃ�΃G���[
	if(NamChk_colon(h)==false){
		return -1;
	}

	//��
	m = bf.substring(bf.lastIndexOf(":")+1);
	//�����ꌅ�Ȃ�ΑO�[��������
	if(m.length == 1){
		m = "0" + m;
	//�񌅂Ȃ�΂��̂܂�
	}else if(m.length == 2){
		m = m;
	//����ȊO�Ȃ�΃G���[
	}else{
			return -1;
	}
	//���l�ƃR�����ȊO�Ȃ�΃G���[
	if(NamChk_colon(m)==false){
		return -1;
	}

	//���������Ԃ��H
	if(eval(h) < 0 || eval(h) > 23 || eval(m) < 0 || eval(m) > 59){
			return -1;
	}

	str = h + ":" + m;
	return str;
}

//**********************************************************
//���ԃ`�F�b�N �f�[�^(hhmm)�̑Ó���
//�����Fstr:������(hhmm)
//�߂�l�F����F������(hhmm) �ُ�F-1
//�T�v�F�S�p���̓f�[�^�͔��p�f�[�^�ɕϊ�
//			ZenToHan_Num()�֐��𗘗p
//**********************************************************
function TimeChk2(str) {

	str2 = "";

	if (str.length == 4 || str.length == 3) {
		str = ZenToHan_Num(str);
		if(str.length==3){
			// 3���̏ꍇ�擪1���������Ԃɐݒ肷��B
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
//���t�́u/�v�ҏW�i���p�j
//����	�FInstr:������ mode:�t���O
//�߂�l�FOutstr:�u/�v�ҏW Instr:�ϊ��s�\�̏ꍇ
//�T�v	�F�����ȊO�̏ꍇ�́A���������̂܂ܕԂ�
//				mode��YYYY/MM/DD��YYYY/MM���̃t���O
//				1:YYYY/MM/DD , 2:YYYY/MM
//				Get_Byte_Kana()�֐��𗘗p
//********************************************************************
function ConvSlash(Instr,mode) {
	var Outstr;
	var cnt;
	Outstr = "";
	Sign_Flg = 0;
	cnt = 0;

	ret=Get_Byte_Kana(Instr);		//�o�C�g���擾
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
//���t�́u/�v�폜�i���p�j
//����	�FInstr:������
//�߂�l�FOutstr:�u/�v�ҏW Instr:�ϊ��s�\�̏ꍇ
//�T�v	�F�����ȊO�̏ꍇ�́A���������̂܂ܕԂ�
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
//���t�[���ҏW
//����	�Fdt:������
//�߂�l�FYYYY/M/D or YYYY/MM/D or YYYY/M/DD �� YYYY/MM/DD�ɕϊ�����������
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
//���z�̃J���}�폜
//����	�Fplace:���z
//�߂�l�Fd:���z
//�T�v	�F�l�̃J���}","���폜����i���z�\���j
//********************************************************************
function delComma(place){
	d = place.replace(/,/g,"");
	return d;
}

//********************************************************************
//Date�^�ϊ�����
//����	�Fdate:���t
//�߂�l�Fd:Date�I�u�W�F�N�g�A���t�Ƃ��Ĉُ�ȏꍇ��null
//�T�v	�F�����񂩂�Date�^�ɕϊ�����
//********************************************************************
function convertDate(date) {
	var dateObj;
	var dateYear = "";
	var dateMonth = "";
	var dateDay = "";

	if (DateChk(date) != -1) {
		// yyyy/mm/dd�`���Ayyyy/m/d�`���̏ꍇ
		dateYear = date.split("/")[0];
		dateMonth = date.split("/")[1];
		dateDay = date.split("/")[2];

		dateObj = new Date(dateYear, parseInt(dateMonth, 10) - 1, dateDay);
	} else if (DateChk2(date) != -1) {
		// yyyyymmdd�`���̏ꍇ
		dateYear = date.substring(0, 4);
		dateMonth = date.substring(4, 6);
		dateDay = date.substring(6, 8);

		dateObj = new Date(dateYear, parseInt(dateMonth) - 1, dateDay);
	} else if (DateChk3(date) != -1) {
		// yyyy/mm�Ayyyy/m�`���̏ꍇ
		dateYear = date.split("/")[0];
		dateMonth = date.split("/")[1];

		dateObj = new Date(dateYear, parseInt(dateMonth, 10) - 1, 1);
	} else if (DateChk4(date) != -1) {
		// yyyymm�`���̏ꍇ
		dateYear = date.substring(0, 4);
		dateMonth = date.substring(4, 6);

		dateObj = new Date(dateYear, parseInt(dateMonth) - 1, 1);
	} else {
		// ��L�ȊO�́A�����I��
		return null;
	}

	return dateObj;
}

//********************************************************************
//���Ԃ́u:�v�ҏW�i���p�j
//����	�FInstr:������
//�߂�l�FOutstr:�u/�v�ҏW Instr:�ϊ��s�\�̏ꍇ
//�T�v	�F�����ȊO�̏ꍇ�́A���������̂܂ܕԂ�
//				Get_Byte_Kana()�֐��𗘗p
//********************************************************************
function ConvCoron(Instr) {
	var Outstr;
	var cnt;
	Outstr = "";
	Sign_Flg = 0;
	cnt = 0;

	ret=Get_Byte_Kana(Instr);		// �o�C�g���擾
	if(ret != 3 && ret != 4){		// 3�o�C�g�����e�Btime�����Ń`�F�b�N�G���[�ɂȂ�Ȃ�[059]�Ȃǂ��l���B
		return(Instr);
	}
	
	Instr = String(Instr);
	
	// �����̂�3���Ȃ�΁A�擪��"0"�ŕ⊮
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
//���Ԃ́u:�v�폜�i���p�j
//����	�FInstr:������
//�߂�l�FOutstr:�u/�v�ҏW Instr:�ϊ��s�\�̏ꍇ
//�T�v	�F�����ȊO�̏ꍇ�́A���������̂܂ܕԂ�
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
//�G���[���b�Z�[�W�ҏW
//����	�Fstr sMsg�F���ɂȂ郁�b�Z�[�W
//	�Fstr sRep1�FsMsg����%1%�ƒu�������镶����
//	�Fstr sRep2�FsMsg����%2%�ƒu�������镶����
//	�Fstr sRep3�FsMsg����%3%�ƒu�������镶����
//�߂�l�Fstr
//�T�v	�F�����b�Z�[�W��u��������ŕҏW���Ԃ��B
//�X�V�F
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
