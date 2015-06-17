Ext.define('TrabalhoPraticoEsApp.view.pessoa.Pessoa', {
    extend: 'Ext.window.Window',
    alias: 'widget.pessoa',

    requires: [
        'TrabalhoPraticoEsApp.view.pessoa.PessoaController',
        'TrabalhoPraticoEsApp.view.pessoa.PessoaViewModel',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date'
    ],
    
    controller: 'pessoa',
    viewModel: {
        type: 'pessoa'
    },
    width: 480,
    title: 'Cadastro de pessoa',

    dockedItems: [
        {
            xtype: 'form',
            dock: 'top',
            layout: 'auto',
            bodyPadding: 10,
            title: '',
            url: 'pessoa/inserir',
            jsonSubmit: true,
            items: [
                {
                    xtype: 'hidden',
                    name: 'idPessoa'                	
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Nome',
                    name: 'nome'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: 'Sit. Civil',
                    name: 'sitCivil',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['sitCivil', 'desc'],
                        data : [
                            {'sitCivil': 'C', 'desc': 'Casado'},
                            {'sitCivil': 'S', 'desc': 'Solteiro'}
                        ]
                    }),
                    queryMode: 'local',
                    displayField: 'desc',
                    valueField: 'sitCivil'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: 'Sexo',
                    name: 'sexo',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['sexo', 'desc'],
                        data : [
                            {'sexo': 'M', 'desc': 'Masculino'},
                            {'sexo': 'F', 'desc': 'Feminino'}
                        ]
                    }),
                    queryMode: 'local',
                    displayField: 'desc',
                    valueField: 'sexo'
                },
                {
                    xtype: 'datefield',
                    fieldLabel: 'Dat. Nasc.',
                    name: 'dataNascimento'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'CPF',
                    name: 'cpf'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'RG',
                    name:'rg'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Telefone',
                    name: 'telefone'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Celular',
                    name: 'celular'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Email',
                    name: 'email'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Pág. pessoal',
                    name: 'pagPessoal'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Msg. Inst.',
                    name: 'msgInst'
                },
                {
                	xtype: 'form',
                	bodyPadding: 10,
                	title: 'Usuario',
                	name: 'usuario',
                	items: [
                	    {
                	    	xtype: 'textfield',
                	    	fieldLabel: 'Login',
                	    	name: 'login'
                	    },
                	    {
                	    	xtype: 'textfield',
                	    	fieldLabel: 'Senha',
                	    	name: 'senha'
                	    }
		            ]
                }
            ],
            buttons: [
	            {
	                text: 'Gravar',
	                formBind: true,
	                listeners: {
	                    click: 'onSaveClick'
	                }
	            },{
	                text: 'Limpar',
	                listeners: {
	                    click: 'onClearClick'
	                }
	            }
            ]
        }
    ]
});