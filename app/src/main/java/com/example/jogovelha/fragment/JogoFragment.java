package com.example.jogovelha.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jogovelha.R;
import com.example.jogovelha.databinding.FragmentJogoBinding;
import com.example.jogovelha.util.PrefsUtil;

import java.util.Arrays;
import java.util.Random;

public class JogoFragment extends Fragment {

    private FragmentJogoBinding binding;
    private Button[] botoes;
    private String[][] tabuleiro;
    private String simbJog1,simbJog2, simbolo;
    private Random random;
    private AlertDialog excluir;
    private int numJogadas=0;
    private int placarJ1=0, placarJ2=0, placarVelha=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        // instancia o binding
        binding = FragmentJogoBinding.inflate(inflater, container, false);

        // instancia o vetor
        botoes = new Button[25];
        // agrupa os botões no vetor
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt03;
        botoes[4] = binding.bt04;
        botoes[5] = binding.bt10;
        botoes[6] = binding.bt11;
        botoes[7] = binding.bt12;
        botoes[8] = binding.bt13;
        botoes[9] = binding.bt14;
        botoes[10] = binding.bt20;
        botoes[11] = binding.bt21;
        botoes[12] = binding.bt22;
        botoes[13] = binding.bt23;
        botoes[14] = binding.bt24;
        botoes[15] = binding.bt30;
        botoes[16] = binding.bt31;
        botoes[17] = binding.bt32;
        botoes[18] = binding.bt33;
        botoes[19] = binding.bt34;
        botoes[20] = binding.bt40;
        botoes[21] = binding.bt41;
        botoes[22] = binding.bt42;
        botoes[23] = binding.bt43;
        botoes[24] = binding.bt44;


        for (Button bt : botoes){
            bt.setOnClickListener(listenerBotoes);
        }

        tabuleiro = new String[5][5];

        // preencher o tabuleiro com ""
        for (String[] vetor : tabuleiro){
            Arrays.fill(vetor, "");
        }

        random = new Random();

        // define o simbolo dos jogadores
        simbJog1 = PrefsUtil.getSimboloJog1(getContext());
        simbJog2 = PrefsUtil.getSimboloJog2(getContext());

        binding.tvJogador1.setText(getResources().getString(R.string.jogador1, simbJog1));
        binding.tvJogador2.setText(getResources().getString(R.string.jogador2, simbJog2));

        sorteia();
        atualizaVez();

        AlertDialog.Builder okExcluir = new AlertDialog.Builder(getContext());
        okExcluir.setTitle("Atenção!!");
        okExcluir.setMessage("Tem certeza que deseja resetar o jogo?");
        okExcluir.setCancelable(false);
        okExcluir.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                placarJ1 = 0;
                placarJ2 = 0;
                placarVelha = 0;
                reset();
                atualizaPlacar();
            }

        });

        okExcluir.setNegativeButton("Não", null);
        excluir = okExcluir.create();

        // retorna a view do Fragment
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        minhaActivity.getSupportActionBar().show();
        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    // metodo para sortear quem joga primeiro
    private void sorteia(){
        if(random.nextBoolean()){
            simbolo = simbJog1;
        }else{
            simbolo = simbJog2;
        }
    }

    private void atualizaVez(){
        if(simbolo.equals(simbJog1)){
            binding.linearJogador1.setBackgroundColor(getResources().getColor(R.color.cinza));
            binding.linearJogador2.setBackgroundColor(getResources().getColor(R.color.white));
        }else {
            binding.linearJogador2.setBackgroundColor(getResources().getColor(R.color.cinza));
            binding.linearJogador1.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private boolean vencedor(){
        // verifica se venceu nas linhas
        for(int i = 0; i < 5; i++){
            if(tabuleiro[i][0].equals(simbolo) &&
                    tabuleiro[i][1].equals(simbolo) &&
                        tabuleiro[i][2].equals(simbolo) &&
                            tabuleiro[i][3].equals(simbolo) &&
                                tabuleiro[i][4].equals(simbolo)) {
                return true;
            }
        }
        // verifica se venceu nas colunas
        for(int i = 0; i < 5; i++){
            if(tabuleiro[0][i].equals(simbolo) &&
                    tabuleiro[1][i].equals(simbolo) &&
                        tabuleiro[2][i].equals(simbolo) &&
                            tabuleiro[3][i].equals(simbolo) &&
                                tabuleiro[4][i].equals(simbolo)){
                return true;
            }
        }
        // verifica se venceu nas diagonais
        if(tabuleiro[0][0].equals(simbolo) &&
                tabuleiro[1][1].equals(simbolo) &&
                    tabuleiro[2][2].equals(simbolo) &&
                        tabuleiro[3][3].equals(simbolo) &&
                            tabuleiro[4][4].equals(simbolo)){
            return true;
        }
        if(tabuleiro[0][4].equals(simbolo) &&
                tabuleiro[1][1].equals(simbolo) &&
                    tabuleiro[2][2].equals(simbolo) &&
                        tabuleiro[3][3].equals(simbolo) &&
                            tabuleiro[4][0].equals(simbolo)){
            return true;
        }

        return false;
    }

    private void atualizaPlacar(){
        binding.placar1.setText(placarJ1+"");
        binding.placar2.setText(placarJ2+"");
        binding.velhaPlacar.setText(placarVelha+"");
    }

    private void reset(){
        for (String[] vetor : tabuleiro){
            Arrays.fill(vetor, "");
        }

        for (Button b : botoes){
            b.setBackgroundColor(getResources().getColor(R.color.black));
            b.setText("");
            b.setClickable(true);
        }
        sorteia();
        atualizaVez();
        numJogadas=0;
   }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_resetar:
                    excluir.show();
                break;

            case R.id.menu_preferencias:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_prefFragment);
                break;
        }

        return  true;
    }


    private View.OnClickListener listenerBotoes = button -> {
        // incrementa as jogadas
        numJogadas++;

        // pega o nome do botão
        String nomeBotao = getContext().getResources().getResourceName(button.getId());
        // extrai os 2 últimos caracteres do nomeBotao
        String posicao = nomeBotao.substring(nomeBotao.length()-2);
        // extrai a posição em linha e coluna
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));

        // marca no tabuleiro o simbolo que foi jogado
        tabuleiro[linha][coluna] = simbolo;
        // faz um casting de View para Button
        Button botao = (Button) button;
        // trocar o símbolo do botão que foi clicado
        botao.setText(simbolo);
        // desabilotar o botão que ja foi apertado
        botao.setClickable(false);
        botao.setBackgroundColor(Color.GRAY);
        // verifica se venceu

        if(numJogadas >= 9 && vencedor()) {
            Toast.makeText(getContext(), R.string.vencedor, Toast.LENGTH_SHORT).show();
            if(simbolo == simbJog1){
                placarJ1++;
            }else{
                placarJ2++;
            }
            atualizaPlacar();
            reset();

        }else if(numJogadas == 25){
            Toast.makeText(getContext(), R.string.velha, Toast.LENGTH_SHORT).show();
            placarVelha++;
            atualizaPlacar();
            reset();

        }else{
            // inverte a vez
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;
            atualizaVez();
        }



    };
}