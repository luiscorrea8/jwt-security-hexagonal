

## los responsables del código sala 4

### responsable de capa domain
>- Luis Alberto Correa Yancel

#### responsable de capa infraestructura
>- Kevin Briceño Quezada

### responsable de la capa application
>- Luis de la Peña

## Pruebas de los endpoints de AuthController

### Login

```bash
curl -X POST http://localhost:9100/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"juan@example.com","password":"password123"}'
```

### Registro

```bash
curl -X POST http://localhost:9100/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan","email":"juan@example.com","password":"password123","role":"CUSTOMER"}'
```

### Notas

- Asegúrate de que la aplicación esté corriendo en `http://localhost:9100`.
- El login devuelve `access_token`, `refresh_token`, `token_type` y `expires_in`.
- Si los datos son inválidos, el endpoint devuelve `400` o `401` según el caso.
