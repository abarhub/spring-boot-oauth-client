import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ProduitModel} from "../model/produit.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class ProduitService {

  private produitUrl='api/produits';

  constructor(private http: HttpClient) {
  }

  getProduits(): Observable<ProduitModel[]> {
    return this.http.get<ProduitModel[]>(this.produitUrl);
  }
}
