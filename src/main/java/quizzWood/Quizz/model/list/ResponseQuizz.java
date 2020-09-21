package quizzWood.Quizz.model.list;

import java.util.List;

public class ResponseQuizz {

    private List<ResponseUtilisat> reponseQuizz;

    public List<ResponseUtilisat> getReponseQuizz() {
        return reponseQuizz;
    }

    /**
     * @param reponseQuizz the persons to set
     */
    public void setPersons(List<ResponseUtilisat> reponseQuizz) {
        this.reponseQuizz = reponseQuizz;
    }
}
