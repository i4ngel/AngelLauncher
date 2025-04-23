import os
import sys
import subprocess

# Verifica e instala dependencias automáticamente
def instalar_dependencias():
    try:
        import minecraft_launcher_lib
    except ImportError:
        print("Instalando dependencia: minecraft_launcher_lib...")
        try:
            subprocess.check_call([sys.executable, "-m", "pip", "install", "minecraft-launcher-lib"])
            import minecraft_launcher_lib  # Reimportar después de instalar
        except Exception as e:
            print(f"Error al instalar la dependencia: {e}")
            sys.exit(1)
    return minecraft_launcher_lib

minecraft_launcher_lib = instalar_dependencias()

# ================= Lógica del launcher (como ya tenías) ====================

NOMBRE_LAUNCHER = ".LauncherAngel"
user_window = os.environ['username']
minecraft_directory = os.path.join(f"C:/Users/{user_window}/AppData/Roaming", NOMBRE_LAUNCHER)

if not os.path.exists(minecraft_directory):
    os.makedirs(minecraft_directory)

def instalar_minecraft(version):
    try:
        minecraft_launcher_lib.install.install_minecraft_version(version, minecraft_directory)
    except Exception as e:
        print(f"Error al instalar Minecraft {version}: {e}")
        raise

def iniciar_minecraft(nombre, ver):
    versiones_instaladas = [v['id'] for v in minecraft_launcher_lib.utils.get_installed_versions(minecraft_directory)]
    if ver not in versiones_instaladas:
        instalar_minecraft(ver)

    options = {
        "username": nombre,
        "uuid": "",
        "token": "",
        "jvmArguments": ["-Xmx2G", "-Xms1G"],
        "launcherVersion": "0.0.2",
    }

    try:
        minecraft_command = minecraft_launcher_lib.command.get_minecraft_command(ver, minecraft_directory, options)
        subprocess.run(minecraft_command)
    except Exception as e:
        print(f"No se pudo iniciar Minecraft: {e}")

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Argumentos insuficientes. Se esperaban: nombre_usuario version")
        sys.exit(1)

    nombre_usuario = sys.argv[1]
    version_minecraft = sys.argv[2]

    iniciar_minecraft(nombre_usuario, version_minecraft)
