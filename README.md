# Api Aluno - Teste Pamcary

## Documentação:
http://localhost:8000/swagger-ui.html

## Salvar aluno:

Url: http://localhost:8000/transactions -> POST

### Exemplo de Body: 

{
  "nome": "David",
  "idade": 34
}

## Obter todos os alunos:

Url: http://localhost:8000/aluno -> GET

### Exemplo de retorno: 

[
    {
        "id": 1,
        "nome": "David",
        "idade": 34
    },
    {
        "id": 4,
        "nome": "Fulano",
        "idade": 30
    }
]

## Obter aluno por id:

Url: http://localhost:8000/aluno/1 -> GET

### Exemplo de retorno: 

{
    "id": 1,
    "nome": "David",
    "idade": 34
}

## Atualizar um aluno:

Url: http://localhost:8000/aluno/1 -> PUT

### Exemplo de Body: 

{
  "nome": "Fulano",
  "idade": 34
}

### Exemplo de retorno: 

{
    "id": 1,
    "nome": "Fulano",
    "idade": 34
}

## Remover um aluno:

Url: http://localhost:8000/aluno/1 -> DELETE






