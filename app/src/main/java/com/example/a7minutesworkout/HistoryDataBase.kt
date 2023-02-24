package com.example.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {

        /**
         * INSTANCE manterá uma referência a qualquer banco de dados retornado via getInstance.
         *
         * Isso nos ajudará a evitar inicializar repetidamente o banco de dados, o que é caro.
         *
         * O valor de uma variável volátil nunca será armazenado em cache e todas as gravações e
         * as leituras serão feitas de e para a memória principal. Isso significa que as mudanças feitas por um
         * encadeamento para dados compartilhados são visíveis para outros encadeamentos.
         */
        @Volatile
        private var INSTANCE: HistoryDataBase? = null

        /**
         * Função auxiliar para obter o banco de dados.
         *
         * Se um banco de dados já foi recuperado, o banco de dados anterior será retornado.
         * Caso contrário, crie um novo banco de dados.
         *
         * Esta função é thread-safe e os chamadores devem armazenar em cache o resultado para vários bancos de dados
         * chamadas para evitar sobrecarga.
         *
         * Este é um exemplo de padrão Singleton simples que usa outro Singleton como
         * argumento em Kotlin.
         *
         * @param context O contexto do aplicativo Singleton, usado para obter acesso ao sistema de arquivos.
         */
        fun getInstance(context: Context): HistoryDataBase {

            // Vários threads podem solicitar o banco de dados ao mesmo tempo, certifique-se de apenas inicializar
            // uma vez usando sincronizado. Apenas um thread pode entrar em um bloco sincronizado em um tempo.
            synchronized(this) {

                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDataBase::class.java,
                        "history_database"
                    )
                        // Limpa e reconstrói em vez de migrar se não houver objeto Migration.
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}