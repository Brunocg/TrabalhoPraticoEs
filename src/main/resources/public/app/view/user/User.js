Ext.define('TrabalhoPraticoEsApp.view.user.User', {
	extend: 'Ext.window.Window',
	alias: 'view.user',
    xtype: 'user',

    requires: [
        'TrabalhoPraticoEsApp.view.user.UserController'
    ],
    
	title: 'Cadastrar',	
	layout: 'fit',
	autoShow: true,
	controller: 'user',
	items: [{
		xtype: 'form',
		bodyPadding: 10,
		defaults: {
			xtype:'textfield',
			anchor: '100%'
		},
		items: [{
			name: 'nome',
			fieldLabel: 'Nome:',
			itemId: 'nome'
		},
		{
			name: 'rua',
			fieldLabel: 'Rua:'
		},
		{
			name: 'numero', 
			fieldLabel: 'Número:',
			vtype: 'alphanum'
		},
		{
			name: 'bairro',
			fieldLabel: 'Bairro:'
		},
		{
			name: 'cidade',
			fieldLabel: 'Cidade:'
		},
		{
			name: 'uf',
			fieldLabel: 'U.F.:'
		},
		{
			name: 'cep',
			fieldLabel: 'CEP:'
		},
		{
			name: 'dataNascimento',
			fieldLabel: 'Nascimento'
		},
		{
			name: 'cpf',
			fieldLabel: 'CPF',
			vtype: 'alphanum'
		},
		{
			name: 'inscricaoRg',
			fieldLabel: 'Inscr./R.G:'
		},
		{
			name: 'ddd',
        	fieldLabel: 'DDD',
        	vtype: 'alphanum'
        },
    	{

	        name: 'telefone',
	        fieldLabel: 'Telefone',
	        vtype: 'alphanum'
        },
    	{
    		name: 'email',
	        fieldLabel: 'Email',
	        vtype: 'email'
	        
        },
    	{
    		name: 'observacao',
	        fieldLabel: 'Observação'
	        
        }
		]
	}],
	dockedItems:[{
		pack: 'end',
		dock: 'bottom',
		items:[{
			xtype: 'button',
			itemId: 'cancel',
			text: 'Cancelar',
			handler: 'onClickLimpaTela'
		},
		{
			xtype: 'button',
			text: 'Salvar',
			itemId: 'save',
			handler: 'onClickSalvar'
		}]
	}]
});