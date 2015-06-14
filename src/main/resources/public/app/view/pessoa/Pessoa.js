Ext.define('TrabalhoPraticoEsApp.view.pessoa.Pessoa', {
    extend: 'Ext.window.Window',
    alias: 'widget.pessoa',

    requires: [
        'TrabalhoPraticoEsApp.view.pessoa.PessoaViewModel',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.grid.Panel',
        'Ext.grid.column.Number',
        'Ext.view.Table',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button'
    ],

    viewModel: {
        type: 'pessoa'
    },
    width: 780,
    title: 'Cadastro de pessoa',

    dockedItems: [
        {
            xtype: 'form',
            dock: 'top',
            layout: 'auto',
            bodyPadding: 10,
            title: '',
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Nome'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: 'Sit. Civil',
                    name: 'sitCivil',
                    value: [
                        'Casado',
                        'Solteiro'
                    ]
                },
                {
                    xtype: 'combobox',
                    fieldLabel: 'Sexo',
                    name: 'sexo',
                    value: [
                        'Masculino',
                        'Feminino'
                    ]
                },
                {
                    xtype: 'datefield',
                    fieldLabel: 'Dat. Nasc.',
                    name: 'dataNascimento'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'CPF'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'RG'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Telefone'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Celular'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Email'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Pág. pessoal'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Msg. Inst.'
                },
                {
                    xtype: 'gridpanel',
                    height: '100%',
                    title: 'Endereços',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'Rua'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'Bairro'
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'number',
                            text: 'Número'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'Cidade'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'UF'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'País'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'CEP'
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    text: 'Adicionar'
                                },
                                {
                                    xtype: 'button',
                                    text: 'Remover'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    height: '100%',
                    title: 'Competências',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'Rua'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'Bairro'
                        },
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'number',
                            text: 'Número'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'Cidade'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'UF'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'País'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'string',
                            text: 'CEP'
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    text: 'Adicionar'
                                },
                                {
                                    xtype: 'button',
                                    text: 'Remover'
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ],
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

});