Ext.define('TrabalhoPraticoEsApp.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',
    
    onSignInClick: function(){     
    	var vm = this.getViewModel();
        TrabalhoPraticoEsApp.view.login.LoginViewModel.efetuarLogin(
	        vm.get('login'), 
	        vm.get('senha'), 
	        function(){
	            localStorage.setItem('LoggedIn', true);
	            this.getView().destroy();
	            Ext.widget('main');
	        },
	        function(){
	        	console.log('fail');
	        },
	        this
        );
    },

	onSignUpClick: function(){        
		Ext.create('TrabalhoPraticoEsApp.view.user.User').show();
	}
});