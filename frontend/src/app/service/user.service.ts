import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserModel} from "../model/user.model";
import {HttpClient} from "@angular/common/http";
import {ProduitModel} from "../model/produit.model";

@Injectable({
  providedIn: 'root',
})
export class UserService {

  private userUrl='api/users';

  constructor(private http: HttpClient) {
  }

  getUser():Observable<UserModel> {
    return this.http.get<UserModel>(this.userUrl+'/connecte');
  }

}
