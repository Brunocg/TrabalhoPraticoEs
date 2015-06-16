Ext.define('TrabalhoPraticoEsApp.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',
    
    onSignInClick: function(btn){     
    	var form = btn.up('form');
        TrabalhoPraticoEsApp.view.login.LoginViewModel.efetuarLogin(
	        form, 
	        function(conn, request) {
	        	var result = Ext.JSON.decode(request.response.responseText, true);
	        	
	        	if (result.success) {
		            localStorage.setItem('LoggedIn', true);
		            form.up('window').close();
		            Ext.widget('main');
	        	}else{
					Ext.Msg.show({
					    title:'Erro!',
					    msg: 'Usuário ou senha está incorreto.',
					    icon: Ext.Msg.ERROR,
					    buttons: Ext.Msg.OK
					});
	        	}
	        },
	        function(){
	        	console.log('fail');
	        }
        );
    },

	onSignUpClick: function(){        
		Ext.create('TrabalhoPraticoEsApp.view.pessoa.Pessoa').show();
	}
});