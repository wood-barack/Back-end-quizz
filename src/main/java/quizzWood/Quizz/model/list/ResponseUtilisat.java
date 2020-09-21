package quizzWood.Quizz.model.list;

public class ResponseUtilisat {

    private String reponse;
    private Long idQuestion;
    private int tempsReponse;

    public String getReponse() {
        return reponse;
    }

    public int getTempsReponse(){
        return tempsReponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setAge(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public void setTempsReponse(int tempsReponse){
        this.tempsReponse = tempsReponse;
    }
}
