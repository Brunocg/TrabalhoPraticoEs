// Funcao para formatar dados do form html
function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function obterPessoa($id) {
	$.ajax({
	    type: "post",
	    url: "/pessoa/obter",
	    // The key needs to match your method's input parameter (case-sensitive).
	    data: JSON.stringify({id:$id}),
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data){
	    	if(data.data != null) {
	    		alert(data);
	    	}
    	},
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
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

function listarGerentes() {
	$.ajax({
	    type: "GET",
	    url: "/pessoa/listar",
	    success: function(data){
	    	if(data.data == null) {
	    		$msg = 'Erro ao listar usuários!';
	    		$type = 'error';
	    		showMessage($msg, $type);
	    	} else {
	    		alert(data.data);
	    	}
    	},
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
}

//Funcoes iniciais
$(document).ready(function() {
	
	//listarGerentes();
	
	$.ajax({
	    type: "GET",
	    url: "/pessoa/usuario/logado",
	    success: function(data){
	    	if(data.data == null) {
	    		window.location.href="/";
	    	} else {
	    		obterPessoa(data.data);
	    	}
    	},
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
	
	// Mostrar nome de quem esta logado
	$(".first-name").html("Erick");
	$(".last-name").html("Previato");
	
	// Funcoes de menu
	$(".dropdown-toggle").dropdown();
	
	// Funcoes da parte de gerente
	$(".btn-gerente-novo").on("click", function(){
		$("#page-wrapper").load("resources/view/gerente/cadastro.html");
		$(".navbar-header button").click();
	});
	$(".btn-gerente-todos").on("click", function(){
		$("#page-wrapper").load("resources/view/gerente/listar.html");
		$(".navbar-header button").click();
	});
	
	$("#gerenteForm").on("submit", function(){
		$.ajax({
		    type: "POST",
		    url: "pessoa/inserir/basico",
		    // The key needs to match your method's input parameter (case-sensitive).
		    data: JSON.stringify(getFormData($(this))),
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    success: function(data){
		    	if(data.success == false) {
		    		$msg = 'Erro ao salvar os dados! Tente novamente mais tarde!';
		    		$type = 'error';
		    		showMessage($msg, $type);
		    	} else {
		    		$msg = 'Dados salvos com sucesso!';
		    		$type = 'success';
		    		showMessage($msg, $type);
		    		$("#page-wrapper").load("resources/view/gerente/cadastro.html");
		    	}
	    	},
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
		return false;
	});
	
	$(".listagem-gerentes button").tooltip();
	
	$("#tableGerente").dataTable();
	
});