function soloNumerosInpuText(e) {

  var keynum = window.event ? window.event.keyCode : e.which;
  if ((keynum == 8) || (keynum == 46))
    return true;

  return /\d/.test(String.fromCharCode(keynum));
}

function valorMaximoInpuText(valor, valorMaximo) {
  alert(valorMaximo);
  alert(valor);
  return true;
}

function validarValorPesos(val) {
  entro('valida');
  var reg = new RegExp('^[0-9]{1,3}(,[0-9]{1,3})?$');
  if (!reg.test(val))
    return false;
  return true;
}

function validarNumero2Decimales(e, field) {
  var key = e.keyCode ? e.keyCode : e.which;

  if (key === 8) {
    return true;
  }

  if (key > 47 && key < 58) {
    if (field.value === "") {
      return true;
    }
    regexp = /.[0-9]{2}$/;
    return !(regexp.test(field.value));
  }

  if (key === 46) {
    if (field.value === "") {
      return false;
    }
    regexp = /^[0-9]+$/;
    return regexp.test(field.value);
  }

  return false;
}

function validacionCamposDialog(xhr, status, args, forma) {
    //alert(args.continuar);
        if(args.validationFailed || !args.continuar) {
            forma.jq.effect("shake", {times:5}, 100);
        }
        else {
            forma.hide();            
        }
    }
