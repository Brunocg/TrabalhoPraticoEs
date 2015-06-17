Ext.define('TrabalhoPraticoEsApp.view.pessoa.PessoaViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.pessoa',
    
    statics: {
    	gravarPessoa: function(form, success, failure){
    		form.submit({
    			method: 'POST', 
    			waitTitle: 'Connecting', 
    			waitMsg: 'Gravando...',
    			success: success,
    			failure: failure
    		});
        }
    }
});