import {Component, OnInit} from '@angular/core';
import {ProduitService} from "./service/produit.service";
import {ProduitModel} from "./model/produit.model";
import {UserService} from "./service/user.service";
import {UserModel} from "./model/user.model";
import {AuthConfig, OAuthService} from "angular-oauth2-oidc";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'frontend';

  listeProduits: ProduitModel[]=[];
  user:UserModel|null=null;

  private authCodeFlowConfig: AuthConfig = {
    // Url of the Identity Provider
    //issuer: 'https://idsvr4.azurewebsites.net',
    issuer: 'http://localhost:8180/auth/realms/test1',

    // URL of the SPA to redirect the user to after login
    //redirectUri: window.location.origin + '/index.html',
    redirectUri: 'http://localhost:4200/',

    // The SPA's id. The SPA is registerd with this id at the auth-server
    // clientId: 'server.code',
    clientId: 'testspa',

    // Just needed if your auth server demands a secret. In general, this
    // is a sign that the auth server is not configured with SPAs in mind
    // and it might not enforce further best practices vital for security
    // such applications.
    // dummyClientSecret: 'secret',

    responseType: 'code',

    // set the scope for the permissions the client should request
    // The first four are defined by OIDC.
    // Important: Request offline_access to get a refresh token
    // The api scope is a usecase specific one
    //scope: 'openid profile email offline_access api',
    scope: 'openid profile email offline_access',

    showDebugInformation: true,
  };

  constructor(private produitService: ProduitService, private userService: UserService,
              private oauthService: OAuthService) {
    this.oauthService.configure(this.authCodeFlowConfig);
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  ngOnInit(): void {
    this.produitService.getProduits().subscribe(data=> {
      this.listeProduits=data;
    },error => {
      console.log("Erreur get produit",error);
    });
  }


  login() {
    this.oauthService.initCodeFlow();
  }

  logout() {
    //this.oauthService.revokeTokenAndLogout();
    this.oauthService.logOut();
  }

  updateUser() {
    this.userService.getUser().subscribe(data =>{
      if(!data||!data.connecte){
        this.user=null;
      } else {
        this.user=data;
      }
    },error => {
      console.log("Erreur get user",error);
    })
  }
}
