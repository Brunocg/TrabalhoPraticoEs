Ext.define('TrabalhoPraticoEsApp.view.pessoa.PessoaController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.pessoa',
    
    onSaveClick: function(btn){     
    	var form = btn.up('form');
        TrabalhoPraticoEsApp.view.pessoa.PessoaViewModel.gravarPessoa(
	        form, 
	        function(conn, request) {
				Ext.Msg.show({
					title:'Sucesso!',
					msg: 'Usuário cadastrado com sucesso!',
					icon: Ext.Msg.OK,
					buttons: Ext.Msg.OK
				});
	        },
	        function(conn, request){
				Ext.Msg.show({
				    title:'Erro!',
				    msg: 'Usuário ou senha está incorreto.',
				    icon: Ext.Msg.ERROR,
				    buttons: Ext.Msg.OK
				});
	        }
        );
    },
    onClearClick: function(btn) {
    	btn.up('form').reset();
    }
});
