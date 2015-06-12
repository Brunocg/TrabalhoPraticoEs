Ext.define('TrabalhoPraticoEsApp.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',
    
    onSignInClick: function(){     
    	var vm = this.getViewModel();
        TrabalhoPraticoEsApp.view.login.LoginViewModel.efetuarLogin(
	        vm.get('login'), 
	        vm.get('senha'), 
	        function(conn, response, options, eOpts) {
	        	
	        	var result = Ext.JSON.decode(conn.responseText, true);
	        	
	        	if (result.success) {
		            localStorage.setItem('LoggedIn', true);
		            this.getView().destroy();
		            Ext.widget('main');
	        	}else
					  Ext.Msg.show({
					        title:'Erro!',
					        msg: 'Usuário ou senha está incorreto.',
					        icon: Ext.Msg.ERROR,
					        buttons: Ext.Msg.OK
					  });
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