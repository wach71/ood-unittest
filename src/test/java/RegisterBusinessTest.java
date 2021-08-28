import badcode.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class xxx implements SpeakerRepository {
    @Override
    public  Integer saveSpeaker(Speaker speaker) {
        return 1000;
    }
}

class RegisterBusinessTest {
    @Test
    @DisplayName("ไม่ทำการกำหนดชื่อ จะเกิด exception First name is required")
    public void case01() {
        RegisterBusiness registerBusiness = new RegisterBusiness();
        try {
            registerBusiness.register(null, new Speaker());
        } catch (ArgumentNullException e) {
            if(!e.getMessage().equals("First name is required.")) {
                fail();
            }
        }
    }

    @Test
    @DisplayName("ไม่ทำการกำหนดนามสกุล จะเกิด exception Last name is required.")
    public void case02() {
        RegisterBusiness registerBusiness = new RegisterBusiness();
        try {
            Speaker speaker = new Speaker();
            speaker.setFirstName("jan");
            registerBusiness.register(null, speaker);
        } catch (ArgumentNullException e) {
            if(!e.getMessage().equals("Last name is required.")) {
                fail();
            }
        }
    }

    @Test
    @DisplayName("ไม่ทำการกำหนด email")
    public void case03() {
        RegisterBusiness registerBusiness = new RegisterBusiness();
        try {
            Speaker speaker = new Speaker();
            speaker.setFirstName("jan");
            speaker.setLastName("jan");
            registerBusiness.register(null, speaker);
        } catch (ArgumentNullException e) {
            if(!e.getMessage().equals("Email is required.")) {
                fail();
            }
        }
    }

    @Test
    @DisplayName("ไม่ทำการกำหนดรูปแบบ email email ไม่ถูกต้อง")
    public void case04() {
        RegisterBusiness registerBusiness = new RegisterBusiness();
        try {
            Speaker speaker = new Speaker();
            speaker.setFirstName("jan");
            speaker.setLastName("jan");
            speaker.setEmail("janus");
            registerBusiness.register(null, speaker);
            fail();
        } catch (DomainEmailInvalidException e) {
        }
    }


    @Test
    @DisplayName("สามารถบันทึกข้อมูลได้")
    public void case07() {
        RegisterBusiness registerBusiness = new RegisterBusiness();
        Speaker speaker = new Speaker();
        speaker.setFirstName("jan");
        speaker.setLastName("jan");
        speaker.setEmail("janus@gmail.com");
        int speakerId = registerBusiness.register(new xxx(), speaker);
        assertEquals(1000, speakerId);
    }

    @Test
    @DisplayName("ไม่สามารถบันทึก speaker")
    public void testRegisterWithCantSaveSpeaker() {
        RegisterBusiness registerBusiness = new RegisterBusiness();
        try {
            Speaker speaker = new Speaker();
            speaker.setFirstName("jan");
            speaker.setLastName("kum");
            speaker.setEmail("jan@live.com");
            registerBusiness.register(null, speaker);
        } catch (SaveSpeakerException e) {
            if(!e.getMessage().equals("Can't save a speaker.")) {
                fail();
            }
        }
    }

    @Test
    @DisplayName("ไม่ทำการกำหนดรูปแบบ email email ไม่ถูกต้อง")
    public void case08() {
        RegisterBusiness registerBusiness = new RegisterBusiness();
        try {
            Speaker speaker = new Speaker();
            speaker.setFirstName("jan");
            speaker.setLastName("jan");
            speaker.setEmail("janus@kum.com");
            registerBusiness.register(null, speaker);
            fail();
        } catch (SpeakerDoesntMeetRequirementsException e) {
            if(!e.getMessage().equals("Speaker doesn't meet our standard rules.")) {
                fail();
            }
        }
    }
}