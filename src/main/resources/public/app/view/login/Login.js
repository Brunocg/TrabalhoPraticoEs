Ext.define("TrabalhoPraticoEsApp.view.login.Login",{
    extend: 'Ext.window.Window',
        xtype: 'login',

    requires: [
        'TrabalhoPraticoEsApp.view.login.LoginController',
        'Ext.form.Panel'
    ],

    controller: 'login',
    bodyPadding: 10,
    title: 'Login Window',
    closable: false,
    autoShow: true,

    items: {
        xtype: 'form',
        reference: 'form',
        items: [{
            xtype: 'textfield',
            name: 'username',
            fieldLabel: 'Usuário',
            allowBlank: false
        }, {
            xtype: 'textfield',
            name: 'password',
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
                click: 'onLoginClick'
            }
        },{
            text: 'Cadastrar colaborador',
            formBind: true,
            listeners: {
                click: 'onLoginClick'
            }
        }]
    }
});