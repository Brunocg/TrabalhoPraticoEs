function obterPessoa($id) {
	$.ajax({
	    type: "get",
	    url: "/pessoa/obter?id="+$id,
	    success: function(data){
	    	if(data.data != null) {
	    		$nome = data.data.nome.split(" ");
	    		$f_name = $nome[0];
	    		$l_name = $nome[$nome.length -1];
	    		$(".first-name").html($f_name);
	    		$(".last-name").html($l_name);	    	}
    	},
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
}

function editarPessoa($id) {
	$("#page-wrapper").load("resources/view/gerente/cadastro.html");
	$('html, body').animate({ scrollTop: 0 }, 200);

	$.ajax({
	    type: "get",
	    url: "/pessoa/obter?id="+$id,
	    success: function(data){
	    	if(data.data != null) {
	    		$("#nome").val(data.data.nome);
	    		$("#dataNascimento").val(data.data.dataNascimento);
	    		$("#cpf").val(data.data.cpf);
	    		$("#rg").val(data.data.rg);
	    		$("#telefone").val(data.data.telefone);
	    		$("#celular").val(data.data.celular);
	    		$("#email").val(data.data.email);
	    		$("#pagPessoal").val(data.data.pagPessoal);
	    		$("#msgInst").val(data.data.msgInst);
	    		$("#login").val(data.data.usuario.login);
	    		$("#idPessoa").val($id);
	    		
	    		$(".subtitulo").html("Editar usuário");
	    		$(".btn-cadastrar-usuario").val("Editar");
	    	}
    	},
	    failure: function(errMsg) {
	    	$msg = 'Erro ao carregar dados da pessoa!';
    		$type = 'error';
    		showMessage($msg, $type);
	    }
	});
	
	iniciarForms();
}

function excluirPessoa($id) {
	$.ajax({
		type: "GET",
	    url: "pessoa/excluir?id="+$id	,
	    success: function(data){
	    	if(data.success == true) {
	    		$msg = 'Dados excluídos com sucesso!';
	    		$type = 'success';
	    		showMessage($msg, $type);
	    		showListGerente();
	    	} else {
	    		$msg = 'Erro ao excluir pessoa!';
	    		$type = 'error';
	    		showMessage($msg, $type);
	    	}
    	},
	    failure: function(errMsg) {
	    	$msg = 'Erro ao carregar dados da pessoa!';
    		$type = 'error';
    		showMessage($msg, $type);
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

function iniciarBotoes(){
	$(".btn-novo").on("click", function(){
		var $tipo = $(this).attr('tipo');
		$.get("resources/view/"+$tipo+"/cadastro.html", function(data){
			$("#page-wrapper").html(data);
			iniciarForms();
	    });
		$('html, body').animate({ scrollTop: 0 }, 200);
	});
	$(".btn-todos").on("click", function(){
		var $tipo = $(this).attr('tipo');
		$.get("resources/view/"+$tipo+"/listar.html", function(data){
			$("#page-wrapper").html(data);
			iniciarForms();			
	    });
		$('html, body').animate({ scrollTop: 0 }, 200);
		if ($tipo != 'projeto') {
			listar('pessoa');
		} else {
			listar($tipo);
		}	
	});
	$(".btn-perfil").on("click", function(){
		$("#page-wrapper").load("resources/view/pessoa/perfil.html");
		$('html, body').animate({ scrollTop: 0 }, 200);
	})
}

function iniciarForms() {
	// Funcao para formatar dados do form html
	function getFormData($form){
	    var unindexed_array = $form.serializeArray();
	    var indexed_array = {};

	    $.map(unindexed_array, function(n, i){
	        indexed_array[n['name']] = n['value'];
	    });

	    return indexed_array;
	}

	// Inserir um novo gerente
	$("#gerenteForm").on("submit", function(){
		$url = ($(".btn-cadastrar-usuario").val() == "Editar") ? "pessoa/editar" : "pessoa/inserir/basico";
		
		var $pessoa = getFormData($(this));
		$usuario = {login:$("#login").val(), senha:$("#senha").val(), usuarioTipo:"Colaborador"};
		$pessoa.usuario = $usuario;
		
		$.ajax({
		    type: "POST",
		    url: $url,
		    data: JSON.stringify($pessoa),
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
		    		$("#page-wrapper").load("resources/view/gerente/listar.html");
		    		listar('pessoa');
		    		$('html, body').animate({ scrollTop: 0 }, 200);
		    	}
	    	},
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
		return false;
	});

	$("#projetoForm").on("submit", function(){
		$.ajax({
		    type: "POST",
		    url: '/projeto/inserir',
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
		    		$("#page-wrapper").load("resources/view/projeto/listar.html");
		    		listar('gerente');
		    		$('html, body').animate({ scrollTop: 0 }, 200);
		    	}
	    	},
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
		return false;
	});
}

function listar($tipo) {
	$.ajax({
	    type: "GET",
	    url: "/"+$tipo+"/listar",
	    success: function(data){
	    	if(data.data == null) {
	    		$msg = 'Erro ao listar usuários!';
	    		$type = 'error';
	    		showMessage($msg, $type);
	    	} else {
	    		$("#table"+$tipo+" tbody").html("");
	    		for (var $i = 0; $i < data.data.length; $i++) {
	    			if ($tipo == 'pessoa') {
		    			var $texto = '<tr role="row">';
		    			$texto += "<td>"+data.data[$i].nome+"</td>";
		    			$texto += "<td>"+data.data[$i].cpf+"</td>";
		    			$texto += "<td>"+data.data[$i].sitCivil+"</td>";
		    			$texto += "<td>"+data.data[$i].sexo+"</td>";
		    			$texto += "<td>"+data.data[$i].dataNascimento+"</td>";
		    			$texto += "<td>"+data.data[$i].estado+"</td>";
		    			$texto += "<td>"+data.data[$i].usuario.login+"</td>";
		    			var $botao1 = '<button class="btn btn-green btn-xs btn-editar" value="'+data.data[$i].idPessoa+'" title="Editar"><i class="fa fa-pencil-square-o"></i></button> &nbsp; ';
				        var $botao2 = '<button class="btn btn-red btn-xs btn-excluir" value="'+data.data[$i].idPessoa+'" title="Excluir"><i class="fa fa-times"></i></button>';
				        $texto += "<td>"+$botao1+$botao2+"</td>";
				        $texto += '</tr>';
	    			} else if ($tipo == 'projeto') {
	    				var $texto = '<tr role="row">';
		    			$texto += "<td>"+data.data[$i].nome+"</td>";
		    			$texto += "<td>"+data.data[$i].tipo+"</td>";
		    			$texto += "<td>"+data.data[$i].prazo+"</td>";
		    			$texto += "<td>"+data.data[$i].observacoes+"</td>";
		    			$texto += "<td>"+data.data[$i].status+"</td>";
		    			var $botao1 = '<button class="btn btn-green btn-xs btn-editar" value="'+data.data[$i].idPessoa+'" title="Editar"><i class="fa fa-pencil-square-o"></i></button> &nbsp; ';
				        var $botao2 = '<button class="btn btn-red btn-xs btn-excluir" value="'+data.data[$i].idPessoa+'" title="Excluir"><i class="fa fa-times"></i></button>';
				        $texto += "<td>"+$botao1+$botao2+"</td>";
				        $texto += '</tr>';
	    			}
			        $("#table"+$tipo+" tbody").append($texto);
	    		}
	    		$(".listagem-gerentes button").tooltip();
	    		$(".btn-editar").on("click", function(){
	    			editarPessoa($(this).val());
	    		});
	    		$(".btn-excluir").on("click", function(){
	    			excluirPessoa($(this).val());
	    		});
	    		$("#table"+$tipo).dataTable();
	    	}
    	},
	    failure: function(errMsg) {
	        alert(errMsg);
	    }
	});
}

//Funcoes iniciais
$(document).ready(function() {
	
	// Obter dados da pessoa logada, ou redireciona para a pagina de login caso nao esteja logada
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
	
	// Funcoes de menu
	$(".dropdown-toggle").dropdown();
	
	iniciarBotoes();
	
	iniciarForms();
	
	// Metodo para encerrar sessao e sair 
	$(".btn-logout").on("click", function(){
		$.ajax({
		    type: "GET",
		    url: "/pessoa/usuario/logout",
		    success: function(data){
		    	if(data.data == null) {
		    		window.location.href="/";
		    	} else {
		    		$msg = 'Erro ao fazer logout!';
		    		$type = 'error';
		    		showMessage($msg, $type);
		    	}
	    	},
		    failure: function(errMsg) {
		        alert(errMsg);
		    }
		});
	});
});