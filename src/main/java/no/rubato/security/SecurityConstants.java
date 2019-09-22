package no.rubato.security;

public class SecurityConstants {
    static final String SIGN_UP_URLS = "/api/register/**";
    static final String IMAGES = "/api/image/**";
    static final String AUDIO = "/api/audio/**";
    static final String VIDEO = "/api/video/**";
    static final String ORDER = "/api/order/**";
    static final String FILE_UPLOAD = "/api/imageUpload/**";
    static final String FILE_DOWNLOAD = "/downloadFile/**";
    static final String VIEW_PERSON = "/api/person/list-all/**";
    static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final long EXPIRATION_TIME = 30000_0000;//30 Seconds
}
