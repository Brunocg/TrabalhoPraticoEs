Ext.define('TrabalhoPraticoEsApp.view.pessoa.PessoaController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.pessoa',
    
    onSaveClick: function(btn){     
    	var form = btn.up('form');
        TrabalhoPraticoEsApp.view.pessoa.PessoaViewModel.gravarPessoa(
	        form, 
	        function(conn, request) {
	        	var result = Ext.JSON.decode(request.response.responseText, true);
	        	
	        	console.log(result);
	        },
	        function(){
	        	console.log('fail');
	        }
        );
    },
    onClearClick: function(btn) {
    	btn.up('form').reset();
    }
});
