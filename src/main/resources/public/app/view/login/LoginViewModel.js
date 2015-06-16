Ext.define('TrabalhoPraticoEsApp.view.login.LoginViewModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.login',  

    statics: {
    	efetuarLogin: function(form, success, failure){
    		form.submit({
    			method:'POST', 
    			waitTitle:'Connecting', 
    			waitMsg:'Login in...',
    			success:success,
    			failure:failure
    	    });
    	}
	}
});