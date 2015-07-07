// Main document to control the app

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function showMessage($msg, $type) {
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'flat'
    }

    Messenger().post({
        message: $msg,
        id: "Only-one-message",
        type: $type,
        showCloseButton: true
    });
}

//Funcoes iniciais
$(document).ready(function() {
	$("#login").focus();
	
	$("#loginForm").on("submit", function(){
		var dados = {login: $("#login").val(), senha: $("#senha").val()};
				
		$.ajax({
		    type: "POST",
		    url: "/pessoa/usuario/login",
		    // The key needs to match your method's input parameter (case-sensitive).
		    data: JSON.stringify(getFormData($(this))),
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){
		    	if(data.success == false) {
		    		$msg = "Dados incorretos!";
		    		$type = "error";
		    		showMessage($msg, $type);
		    		$("#senha").val("");
		    		$("#login").focus();
		    	} else {
		    		$msg = "Login efetuado! Redirecionando...";
		    		$type = "success";
		    		showMessage($msg, $type);
		    		window.location.href="home.html";
		    	}
	    	},
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
		
		return false;
	});
	
	$.ajax({
	    type: "GET",
	    url: "/pessoa/usuario/logado",
	    success: function(data){
	    	if(data.data != null) {
	    		window.location.href="home.html";
	    	}
    	},
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
	
	$(".btn-novo").on("click", function(){
		$(".portlet-green").load("resources/view/pessoa/cadastro/index.html");
	});
});