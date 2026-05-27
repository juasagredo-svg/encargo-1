package com.galiana_project.cl.galiana_project;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.galiana_project.cl.galiana_project.model.Asiento;
import com.galiana_project.cl.galiana_project.model.AsientoBoleta;
import com.galiana_project.cl.galiana_project.model.Boleta;
import com.galiana_project.cl.galiana_project.model.Ciudad;
import com.galiana_project.cl.galiana_project.model.Comuna;
import com.galiana_project.cl.galiana_project.model.Director;
import com.galiana_project.cl.galiana_project.model.Obra;
import com.galiana_project.cl.galiana_project.model.ObraSala;
import com.galiana_project.cl.galiana_project.model.SalaTeatro;
import com.galiana_project.cl.galiana_project.model.Pago;
import com.galiana_project.cl.galiana_project.model.Region;
import com.galiana_project.cl.galiana_project.model.Sala;
import com.galiana_project.cl.galiana_project.model.Teatro;
import com.galiana_project.cl.galiana_project.model.TipoUsuario;
import com.galiana_project.cl.galiana_project.model.Usuario;
import com.galiana_project.cl.galiana_project.repository.AsientoBoletaRepository;
import com.galiana_project.cl.galiana_project.repository.AsientoRepository;
import com.galiana_project.cl.galiana_project.repository.BoletaRepository;
import com.galiana_project.cl.galiana_project.repository.CiudadRepository;
import com.galiana_project.cl.galiana_project.repository.ComunaRepository;
import com.galiana_project.cl.galiana_project.repository.DirectorRepository;
import com.galiana_project.cl.galiana_project.repository.ObraRepository;
import com.galiana_project.cl.galiana_project.repository.ObraSalaRepository;
import com.galiana_project.cl.galiana_project.repository.SalaTeatroRepository;
import com.galiana_project.cl.galiana_project.repository.PagoRepository;
import com.galiana_project.cl.galiana_project.repository.RegionRepository;
import com.galiana_project.cl.galiana_project.repository.SalaRepository;
import com.galiana_project.cl.galiana_project.repository.TeatroRepository;
import com.galiana_project.cl.galiana_project.repository.TipoUsuarioRepository;
import com.galiana_project.cl.galiana_project.repository.UsuarioRepository;

import net.datafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AsientoBoletaRepository asientoBoletaRepository;

    @Autowired
    private AsientoRepository asientoRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private ObraSalaRepository obraSalaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaTeatroRepository salaTeatroRepository;

    @Autowired
    private TeatroRepository teatroRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @SuppressWarnings("deprecation")
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar Regiones
        for (int i = 0; i < 2; i++) {
            Region region = new Region();
            // region.setId(i + 1);
            region.setNombre(faker.address().state());
            regionRepository.save(region);
        }

        // Generar Ciudades
        List<Region> regiones = regionRepository.findAll();
        for (int i = 0; i < 3; i++) {
            Ciudad ciudad = new Ciudad();
            // ciudad.setId(i + 1);
            ciudad.setNombre(faker.address().city());
            ciudad.setRegion(regiones.get(random.nextInt(regiones.size())));
            ciudadRepository.save(ciudad);
        }

        // Generar Comunas
        List<Ciudad> ciudades = ciudadRepository.findAll();
        for (int i = 0; i < 5; i++) {
            Comuna comuna = new Comuna();
            // comuna.setId(i + 1);
            comuna.setNombre(faker.address().secondaryAddress());
            comuna.setCiudad(ciudades.get(random.nextInt(ciudades.size())));
            comunaRepository.save(comuna);
        }

        // Generar Teatros
        List<Comuna> comunas = comunaRepository.findAll();
        for (int i = 0; i < 5; i++) {
            Teatro teatro = new Teatro();
            // teatro.setId(i + 1);
            teatro.setNombre("Teatro " + faker.artist().name());
            teatro.setDireccion(faker.address().streetName());
            teatro.setContacto(faker.phoneNumber().phoneNumber());
            teatro.setComuna(comunas.get(random.nextInt(comunas.size())));
            teatroRepository.save(teatro);
        }

        // Generar ObrasTeatro
        List<Teatro> teatros = teatroRepository.findAll();
        for (int i = 0; i < 20; i++) {
            SalaTeatro salaTeatro = new SalaTeatro();
            // salaTeatro.setId(i + 1);
            salaTeatro.setTeatro(teatros.get(random.nextInt(teatros.size())));
            salaTeatroRepository.save(salaTeatro);
        }

        // Generar Salas
        List<SalaTeatro> salaTeatros = salaTeatroRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Sala sala = new Sala();
            // sala.setId(i + 1);
            sala.setNumSala(i + 1);
            sala.setCapacidad(40);
            sala.setSalaTeatro(salaTeatros.get(random.nextInt(salaTeatros.size())));
            salaRepository.save(sala);
        }
        // Generar Directores
        for (int i = 0; i < 10; i++) {
            Director director = new Director();
            // director.setId(i + 1);
            director.setNombres(faker.name().fullName());
            director.setFechaNacimiento(faker.date().birthday(27, 70));
            directorRepository.save(director);
        }
        // Generar Obras
        List<Director> directores = directorRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Obra obra = new Obra();
            // obra.setId(i + 1);
            obra.setNombre(faker.book().title());

            // Generar horario aleatorio entre 15:00 y 22:00
            int hora = faker.number().numberBetween(15, 22);
            int minutos = faker.options().option(0, 15, 30, 45);
            Time horario = new Time(hora, minutos, 0); // hora, minutos, segundos
            obra.setHorario(horario);

            // Generar fechas aleatorias
            Date fechaInicio = faker.date().future(30,
                    java.util.concurrent.TimeUnit.DAYS);
            // Sumar entre 1 y 10 días a la fecha de inicio para la fecha de término
            Date fechaTermino = new Date(fechaInicio.getTime() +
                    faker.number().numberBetween(1, 10) * 24 * 60 * 60 * 1000L);

            obra.setFechaInicio(fechaInicio);
            obra.setFechaTermino(fechaTermino);

            obra.setPrecio(faker.number().numberBetween(5000, 20000));

            // Opción 1: Genérico
            // obra.setDescripcion(faker.lorem().paragraph(3)); // 3 párrafos

            // Opción 2: Sinopsis temática
            String descripcion = "En esta obra, " + faker.lorem().sentence(10);
            if (descripcion.length() > 150) {
                descripcion = descripcion.substring(0, 150);
            }
            obra.setDescripcion(descripcion);
            obra.setDirector(directores.get(random.nextInt(directores.size())));
            obraRepository.save(obra);
        }

        // Generar ObraSalas
        List<Sala> salas = salaRepository.findAll();
        List<Obra> obras = obraRepository.findAll();
        for (int i = 0; i < 10; i++) {
            ObraSala obraSala = new ObraSala();
            // obraSala.setId(i + 1);
            obraSala.setSala(salas.get(random.nextInt(salas.size())));
            obraSala.setObra(obras.get(random.nextInt(obras.size())));
            obraSalaRepository.save(obraSala);
        }

        // Generar asientos
        salas = salaRepository.findAll();
        for (int i = 0; i < 40; i++) {
            Asiento asiento = new Asiento();
            // asiento.setId(i + 1);
            asiento.setNumAsiento(faker.number().numberBetween(1, 60));
            List<Character> letrasValidas = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F',
                    'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                    'X', 'Y', 'Z');
            asiento.setFila(faker.options().nextElement(letrasValidas));
            asiento.setEstado(faker.bool().bool());
            asiento.setSala(salas.get(random.nextInt(salas.size())));
            asientoRepository.save(asiento);
        }

        // Generar Pagos
        String[] metodosPago = {
                "Tarjeta de Crédito",
                "Tarjeta de Débito",
                "Transferencia Bancaria",
                "Efectivo"
        };
        for (int i = 0; i < 3; i++) {
            Pago pago = new Pago();
            // pago.setId(i + 1);
            pago.setMetodoPago(faker.options().option(metodosPago));
            pagoRepository.save(pago);
        }

        // Generar Tipos de Usuario
        String[] tiposUsuario = {
                "Administrador",
                "Usuario estándar",
                "Invitado"
        };
        for (int i = 0; i < 3; i++) {
            TipoUsuario tipoUsuario = new TipoUsuario();
            // tipoUsuario.setId(i + 1);
            tipoUsuario.setTipoDeUsuario(faker.options().option(tiposUsuario));
            tipoUsuarioRepository.save(tipoUsuario);
        }

        // Generar Usuarios
        List<TipoUsuario> tiposUsuarios = tipoUsuarioRepository.findAll();
        for (int i = 0; i < 20; i++) {
            Usuario usuario = new Usuario();
            // usuario.setId(i + 1);
            usuario.setRut(faker.number().digits(7) + "-" + faker.number().digit());
            usuario.setNombres(faker.name().fullName());
            usuario.setContraseña(faker.internet().password(8, 20, true, true));
            usuario.setFechaNacimiento(faker.date().birthday(11, 80));
            usuario.setMail(faker.internet().emailAddress());
            usuario.setTipoUsuario(tiposUsuarios.get(random.nextInt(tiposUsuarios.size())));
            usuarioRepository.save(usuario);
        }

        // Generar Boletas
        List<Pago> pagos = pagoRepository.findAll();
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Boleta boleta = new Boleta();
            // boleta.setId(i + 1);
            boleta.setFechaBoleta(new Date());
            boleta.setPrecioTotal(faker.number().numberBetween(5000, 50000));
            boleta.setPago(pagos.get(random.nextInt(pagos.size())));
            boleta.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            boletaRepository.save(boleta);
        }
        // Generar BoletaAsiento
        List<Asiento> asientos = asientoRepository.findAll();
        List<Boleta> boletas = boletaRepository.findAll();
        for (int i = 0; i < 10; i++) {
            AsientoBoleta asientoBoleta = new AsientoBoleta();
            // asientoBoleta.setId(i+1);
            asientoBoleta.setAsiento(asientos.get(random.nextInt(asientos.size())));
            asientoBoleta.setBoleta(boletas.get(random.nextInt(boletas.size())));
            asientoBoletaRepository.save(asientoBoleta);
        }

    }
}
