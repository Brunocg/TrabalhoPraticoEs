Ext.define('TrabalhoPraticoEsApp.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',

    onSignInClick: function(){        
        localStorage.setItem("TutorialLoggedIn", true);

        this.getView().destroy();

        Ext.widget('app-main');
    },

	onSignUpClick: function(){        
		Ext.create('TrabalhoPraticoEsApp.view.user.User').show();
	}
});