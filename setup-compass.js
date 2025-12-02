// COPIA Y PEGA ESTOS COMANDOS EN EL MONGOSH SHELL DE COMPASS
// Uno por uno o todos juntos

use lavidaesbella

db.mascotas.drop()
db.usuarios.drop()
db.solicitudes.drop()
db.citas.drop()

db.createCollection("mascotas")

db.mascotas.insertMany([
    { nombre: "Rufus", especie: "Perro", edad: 3, estadoSalud: "Excelente", personalidad: "Amigable y juguetón, le encanta correr y jugar con niños", urlImagen: "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=400", disponible: true },
    { nombre: "Luna", especie: "Gato", edad: 2, estadoSalud: "Muy buena", personalidad: "Tranquila y cariñosa, perfecta para apartamentos", urlImagen: "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=400", disponible: true },
    { nombre: "Max", especie: "Perro", edad: 5, estadoSalud: "Buena", personalidad: "Leal y protector, entrenado para vivir en familia", urlImagen: "https://images.unsplash.com/photo-1552053831-71594a27632d?w=400", disponible: true },
    { nombre: "Michi", especie: "Gato", edad: 1, estadoSalud: "Excelente", personalidad: "Muy activo y curioso, le gusta explorar", urlImagen: "https://images.unsplash.com/photo-1574158622682-e40e69881006?w=400", disponible: true },
    { nombre: "Rocky", especie: "Perro", edad: 4, estadoSalud: "Excelente", personalidad: "Energético y obediente, necesita ejercicio diario", urlImagen: "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?w=400", disponible: true },
    { nombre: "Coco", especie: "Pajaro", edad: 2, estadoSalud: "Excelente", personalidad: "Sociable y parlanchín, aprende palabras rápidamente", urlImagen: "https://images.unsplash.com/photo-1552728089-57bdde30beb3?w=400", disponible: true },
    { nombre: "Bella", especie: "Gato", edad: 3, estadoSalud: "Buena", personalidad: "Independiente pero cariñosa, ideal para personas tranquilas", urlImagen: "https://images.unsplash.com/photo-1573865526739-10c1d3a1f0e3?w=400", disponible: true },
    { nombre: "Thor", especie: "Perro", edad: 6, estadoSalud: "Muy buena", personalidad: "Maduro y tranquilo, excelente compañero para adultos mayores", urlImagen: "https://images.unsplash.com/photo-1568572933382-74d440642117?w=400", disponible: true }
])

db.createCollection("usuarios")

db.usuarios.insertOne({
    contrasena: "123456",
    infoPersonal: {
        nombre: "Usuario Demo",
        correo: "demo@ejemplo.com",
        curp: "DEMO900101HMCXXX00",
        direccion: "Calle Principal #123, Colonia Centro",
        telefono: "6441234567"
    },
    infoVivienda: {
        tipoVivienda: "Casa",
        condicionesHogar: "Espacio amplio con jardín",
        tieneOtrasMascotas: false,
        tieneNinos: false,
        tiempoDisponibilidad: "4-6 horas diarias"
    }
})

db.createCollection("solicitudes")
db.createCollection("citas")

db.mascotas.createIndex({ nombre: 1 })
db.mascotas.createIndex({ disponible: 1 })
db.mascotas.createIndex({ especie: 1 })
db.usuarios.createIndex({ "infoPersonal.correo": 1 }, { unique: true })

print("✅ Base de datos configurada correctamente")
print("Mascotas: " + db.mascotas.countDocuments())
print("Usuarios: " + db.usuarios.countDocuments())
