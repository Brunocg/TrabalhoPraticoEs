Ext.define("TrabalhoPraticoEsApp.view.login.Login",{
    extend: 'Ext.window.Window',
    alias: 'view.login',
    xtype: 'login',

    requires: [
        'TrabalhoPraticoEsApp.view.login.LoginController',
        'TrabalhoPraticoEsApp.view.login.LoginViewModel',
        'Ext.form.Panel'
    ],

    controller: 'login',
	viewModel:{
		type: 'login'
	},
    bodyPadding: 10,
    title: 'Login Window',
    closable: false,
    autoShow: true,

    items: {
        xtype: 'form',
        reference: 'form',
        url: 'pessoa/usuario/login',
        jsonSubmit: true,
        items: [{
            xtype: 'textfield',
            name: 'login',
            fieldLabel: 'Usuário',
            allowBlank: false
        }, {
            xtype: 'textfield',
            name: 'senha',
            inputType: 'password',
            fieldLabel: 'Senha',
            allowBlank: false
        }, {
            xtype: 'displayfield',
            hideEmptyLabel: false,
            value: 'Entre com qualquer senha.'
        }],
        buttons: [{
            text: 'Login',
            formBind: true,
            listeners: {
                click: 'onSignInClick'
            }
        },{
            text: 'Cadastrar usuário',
            listeners: {
                click: 'onSignUpClick'
            }
        }]
    }
});