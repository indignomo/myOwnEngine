package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Super Mario Reborn";

    }

    public static Window get(){
        if(Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;

    }

    public void run(){
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();
    }

    public void init(){
        //Preparar um callback de erro
        GLFWErrorCallback.createPrint(System.err).set();
        
        //Inicializar GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Incapaz de inicializar o GLFW.");
        }

        //Configurar GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Criar a janela
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if(glfwWindow == NULL) {
            throw new IllegalStateException("Falha ao criar janela GLFW.");
        }

        //Fazer o contexto OpenGL
            glfwMakeContextCurrent(glfwWindow);
            //Ativar v-sync
            glfwSwapInterval(1);

            //Fazer a janela ficar visível
            glfwShowWindow(glfwWindow);

            //Essa linha é crítica para a interoperação do LWJGL com o GLFW
            //Contexto OpenGL, ou qualquer contexto que é gerenciado externamente.
            //LWJGL detecta o contexto atual na thread atual,
            //cria a instância GLCapabilities e faz os bindings OpenGL abertos para uso.

            GL.createCapabilities();

    }

    public void loop(){
        while(!glfwWindowShouldClose(glfwWindow)) {
            //Eventos Poll
            glfwPollEvents();

            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }

}
