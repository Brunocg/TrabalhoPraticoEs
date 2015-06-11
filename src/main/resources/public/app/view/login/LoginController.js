Ext.define('TrabalhoPraticoEsApp.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',
    requires: [
        'TrabalhoPraticoEsApp.view.user.UserViewModel'
    ],
    
    onSignInClick: function(){     
        var model = new TrabalhoPraticoEsApp.view.user.UserViewModel();
        var me = this;
        model.enviarNotas(me.getViewModel().get('ambiente'), ids, function(){
        	me.getViewModel().getStore('notasPendentes').reload();
        	me.fireViewEvent('notasPendentesEnviadas');
        	console.log('sucesso!');
        },
        null,
        function(){
        	
        });
    	
        localStorage.setItem("LoggedIn", true);

        this.getView().destroy();

        Ext.widget('main');
    },

	onSignUpClick: function(){        
		Ext.create('TrabalhoPraticoEsApp.view.user.User').show();
	}
});