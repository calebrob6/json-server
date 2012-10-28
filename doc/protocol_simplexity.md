###Simplexity protocol
REQUEST< DATA > => { VALID_RESPONSE(S) }

###Do
    DO_MOVE<COLUMN> => {SUCCESS}
      SUCCESS - A move was successfully made

###Query
    QUERY_ID => {ID}
      ID - ID of the player

    QUERY_TURN => {ID}
      ID - ID of the player who is eligible to make a move

    QUERY_MOVE => {MOVE}
      MOVE - Return all moves that have occurred since the players last move

    QUERY_SCORE => {SCORE}
      SCORE - Respond with win/tie/lose totals for all clients
###Error Response [Do, Query]
All Do and Query requests are responsible for implementing the following responses:

    NO_GAME - No game is actively scheduled
    FAIL - Unspecified error
