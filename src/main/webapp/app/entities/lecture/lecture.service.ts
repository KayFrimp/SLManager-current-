import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILecture } from 'app/shared/model/lecture.model';

type EntityResponseType = HttpResponse<ILecture>;
type EntityArrayResponseType = HttpResponse<ILecture[]>;

@Injectable({ providedIn: 'root' })
export class LectureService {
    public resourceUrl = SERVER_API_URL + 'api/lectures';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/lectures';

    constructor(protected http: HttpClient) {}

    create(lecture: ILecture): Observable<EntityResponseType> {
        return this.http.post<ILecture>(this.resourceUrl, lecture, { observe: 'response' });
    }

    update(lecture: ILecture): Observable<EntityResponseType> {
        return this.http.put<ILecture>(this.resourceUrl, lecture, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ILecture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILecture[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILecture[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
