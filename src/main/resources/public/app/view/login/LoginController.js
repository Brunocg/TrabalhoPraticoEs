Ext.define('TrabalhoPraticoEsApp.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',

    onSignInClick: function(){        
        localStorage.setItem("LoggedIn", true);

        this.getView().destroy();

        Ext.widget('main');
    },

	onSignUpClick: function(){        
		Ext.create('TrabalhoPraticoEsApp.view.user.User').show();
	}
});