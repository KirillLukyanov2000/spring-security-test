
@Configuration
@EnableWebSecurity
public class JwtConfig {

  @Autowired
  private JwtAuthenticationEntryPoint entryPoint;
  @Autowired
  private UserDetailsService userDetailsService;
  @Autiwired
  private JwtRequestFilter requestFilter;

}
